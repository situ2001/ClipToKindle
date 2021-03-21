package com.situ2001.cliptokindle;

import android.app.Application;
import android.util.Log;

import com.situ2001.cliptokindle.bean.SingletonDisplayableList;
import com.situ2001.cliptokindle.util.PageGenerator;
import com.situ2001.cliptokindle.util.Utils;

/**
 * Do preparation before MainActivity creates.
 */
public class ClipToKindleApplication extends Application {
    private final String TAG = "PreApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.setStoragePath(this);
        PageGenerator.build(SingletonDisplayableList.getSingleton());
        Log.e(TAG, PageGenerator.getPageGenerator().generate());
        Utils.setContextWrapper(this);
    }
}
