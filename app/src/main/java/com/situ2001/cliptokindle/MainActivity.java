package com.situ2001.cliptokindle;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;

import com.situ2001.cliptokindle.fragment.RecyclerViewFragment;
import com.situ2001.cliptokindle.text.Text;
import com.situ2001.cliptokindle.text.TextSet;
import com.situ2001.cliptokindle.text.TextSetHelper;
import com.situ2001.cliptokindle.util.HttpApp;
import com.situ2001.cliptokindle.util.PageGenerator;
import com.situ2001.cliptokindle.util.Utils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private HttpApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //show RecycleViewFragment
        RecyclerViewFragment fragment = null;
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = new RecyclerViewFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }

        TextView tvHttpServerStatus = findViewById(R.id.http_server_status);
        Button btClipBoard = findViewById(R.id.get_clip_board_text);
        SwitchCompat switchServer = findViewById(R.id.switch_server);

        ClipboardManager manager = getSystemService(ClipboardManager.class);

        app = new HttpApp();
        switchServer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                try {
                    app.start();
                    tvHttpServerStatus.setText(Utils.getHint(Utils.getWifiStatus(this)));
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

        RecyclerViewFragment finalFragment = fragment; // an effectively final variable
        btClipBoard.setOnClickListener(l -> {
            if (!manager.hasPrimaryClip()) {
                Toast.makeText(this, "No text in clipboard", Toast.LENGTH_SHORT).show();
                return;
            }
            String text = manager.getPrimaryClip().getItemAt(0).getText().toString();
            if (TextSetHelper.get().contains(new Text(text))) {
                Toast.makeText(this, "Item already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            TextSetHelper.get().add(new Text(text));

            finalFragment.getmAdapter().notifyDataSetChanged();
        });

        //listening to clipboard
        manager.addPrimaryClipChangedListener(() -> {
            if (!manager.hasPrimaryClip()) {
                Toast.makeText(this, "No text in clipboard", Toast.LENGTH_SHORT).show();
                return;
            }
            String text = manager.getPrimaryClip().getItemAt(0).getText().toString();
            TextSetHelper.get().add(new Text(text));
            finalFragment.getmAdapter().notifyItemInserted(TextSetHelper.getList().size() - 1);

            Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show();
        });
    }

    private void init() {
        TextSet textSet = TextSetHelper.get();
        Utils.setStoragePath(this);
        textSet.load();
        PageGenerator.build(textSet);
        Log.i(TAG, PageGenerator.getPageGenerator().generate());
    }
}