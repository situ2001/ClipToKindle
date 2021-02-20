package com.example.cliptokindle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.cliptokindle.fragment.RecyclerViewFragment;
import com.example.cliptokindle.text.Text;
import com.example.cliptokindle.text.TextSet;
import com.example.cliptokindle.text.TextSetHelper;
import com.example.cliptokindle.util.HttpApp;
import com.example.cliptokindle.util.PageGenerator;
import com.example.cliptokindle.util.Utils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //show RecycleViewFragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        if (savedInstanceState == null) {
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }

        TextView tvHttpServerStatus = findViewById(R.id.httpServerStatus);
        Button btClipBoard = findViewById(R.id.getClipBoardText);
        SwitchCompat switchServer = findViewById(R.id.switch_server);
        HttpApp app = new HttpApp();

        switchServer.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                try {
                    app.start();
                    tvHttpServerStatus.setText(
                            "Listening on port 8080\nIP address: " + Utils.getIpAddress(this)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                    tvHttpServerStatus.setText("Failed to start server.");
                }
            } else {
                app.stop();
                tvHttpServerStatus.setText("Server is off");
            }
        });
        switchServer.setChecked(true);

        btClipBoard.setOnClickListener(l -> {
            ClipboardManager manager = getApplicationContext().getSystemService(ClipboardManager.class);

            String text;
            if (!manager.hasPrimaryClip()) {
                return;
            }
            text = manager.getPrimaryClip().getItemAt(0).getText().toString();

            TextSetHelper.get().add(new Text(text));

            /* Temporarily not use
            Bundle bundle = new Bundle();
            bundle.putString("content", text);

            DialogFragment dialogFragment = new ClipBoardDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), TAG);
             */

            fragment.getmAdapter().notifyDataSetChanged();
        });
    }

    private void init() {
        //load TextSet and PageGenerator
        TextSet textSet = TextSetHelper.get();
        Utils.setStoragePath(this);
        textSet.load();
        PageGenerator.setTextSet(textSet);
        Log.e("DEBUG", PageGenerator.generate());
    }
}