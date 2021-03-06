package com.situ2001.cliptokindle;

import android.content.ClipboardManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;

import com.situ2001.cliptokindle.bean.DisplayableList;
import com.situ2001.cliptokindle.bean.SingletonDisplayableList;
import com.situ2001.cliptokindle.fragment.RecyclerViewFragment;
import com.situ2001.cliptokindle.bean.text.Text;
import com.situ2001.cliptokindle.util.HttpApp;
import com.situ2001.cliptokindle.util.Utils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static DisplayableList displayableList;
    private HttpApp app;

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
        ClipboardManager manager = getSystemService(ClipboardManager.class);
        TextView tvHttpServerStatus = findViewById(R.id.http_server_status);
        Button btClipBoard = findViewById(R.id.get_clip_board_text);
        SwitchCompat switchServer = findViewById(R.id.switch_server);

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
        RecyclerViewFragment finalFragment = fragment; // an effectively final variable
        btClipBoard.setOnClickListener(l -> {
            if (manager.hasPrimaryClip()) {
                String text = manager.getPrimaryClip().getItemAt(0).getText().toString();
                Text textObj = Text.build(text);
                if (!displayableList.contains(textObj)) {
                    displayableList.add(textObj);
                    //finalFragment.getmAdapter().notifyDataSetChanged();
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
                Text textObj = Text.build(text);
                if (!displayableList.contains(textObj)) {
                    displayableList.add(Text.build(text));
                    finalFragment.getmAdapter().notifyItemInserted(displayableList.size() - 1);
                    Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No text in clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}