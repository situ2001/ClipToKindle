package com.situ2001.cliptokindle;

import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;

import com.situ2001.cliptokindle.bean.DisplayableList;
import com.situ2001.cliptokindle.bean.SingletonDisplayableList;
import com.situ2001.cliptokindle.bean.book.Book;
import com.situ2001.cliptokindle.fragment.RecyclerViewFragment;
import com.situ2001.cliptokindle.bean.link.Link;
import com.situ2001.cliptokindle.util.HttpApp;
import com.situ2001.cliptokindle.util.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static DisplayableList displayableList;
    private HttpApp app;
    private RecyclerViewFragment finalFragment;
    private Map<String, InputStream> fileMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set and show RecycleViewFragment
        RecyclerViewFragment fragment = null;
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = new RecyclerViewFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }

        //initialize the variables
        app = new HttpApp();
        displayableList = SingletonDisplayableList.getSingleton();
        fileMap = displayableList.getMap();
        ClipboardManager manager = getSystemService(ClipboardManager.class);
        TextView tvHttpServerStatus = findViewById(R.id.http_server_status);
        Button btClipBoard = findViewById(R.id.get_clip_board_text);
        SwitchCompat switchServer = findViewById(R.id.switch_server);
        initMap(); // to gain a new InputStream

        switchServer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                try {
                    app.start();
                    tvHttpServerStatus.setText(Utils.getHint(Utils
                            .getWifiStatus(getSystemService(WifiManager.class))));
                } catch (IOException e) {
                    e.printStackTrace();
                    tvHttpServerStatus.setText(R.string.fail_to_start_server);
                   buttonView.setChecked(false);
                }
            } else {
                app.stop();
                tvHttpServerStatus.setText(R.string.server_is_off);
            }
        });
        switchServer.setChecked(true);
        
        //listening to clipboard
        finalFragment = fragment; // an effectively final variable
        btClipBoard.setOnClickListener(l -> {
            if (manager.hasPrimaryClip()) {
                String text = manager.getPrimaryClip().getItemAt(0).getText().toString();
                Link linkObj = Link.build(text);
                if (!displayableList.contains(linkObj)) {
                    displayableList.add(linkObj);
                    finalFragment.getmAdapter().notifyItemInserted(displayableList.size() - 1);
                } else {
                    Toast.makeText(this, "Item already exists", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No text in clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        //listening to clipboard
        manager.addPrimaryClipChangedListener(() -> {
            if (manager.hasPrimaryClip()) {
                String text = manager.getPrimaryClip().getItemAt(0).getText().toString();
                Link linkObj = Link.build(text);
                if (!displayableList.contains(linkObj)) {
                    displayableList.add(Link.build(text));
                    finalFragment.getmAdapter().notifyItemInserted(displayableList.size() - 1);
                    Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No text in clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        // test
        Button button = findViewById(R.id.get_book);
        button.setOnClickListener(l -> openFile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Uri uri = data.getData();
            Book book = new Book(uri, getFileName(uri));
            if (!displayableList.contains(book)) {
                // update fileMap
                try {
                    fileMap.put(book.getTitle(), getContentResolver().openInputStream(book.toUri()));
                    app.updateFileMap(fileMap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(fileMap);

                // add this Book to DisplayableList
                displayableList.add(book);
                finalFragment.getmAdapter().notifyItemInserted(displayableList.size() - 1);

                // will be removed later
                try {
                    InputStream inputStream = getContentResolver().openInputStream(book.toUri());
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    System.out.println(buffer.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Uri.EMPTY);
        startActivityForResult(intent, 1);
    }

    // get file name from Uri
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver()
                    .query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void initMap() {
        // initialize the fileMap
        displayableList.forEach(displayable -> {
            if (displayable instanceof Book) {
                try {
                    fileMap.put(displayable.getTitle(),
                            getContentResolver().openInputStream(((Book) displayable).toUri()));
                    app.updateFileMap(fileMap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
