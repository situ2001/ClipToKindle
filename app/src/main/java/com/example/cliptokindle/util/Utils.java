package com.example.cliptokindle.util;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.File;

public class Utils {
    private static File path;

    /**
     * Get IP address oof Wifi
     * @param context
     * @return
     */
    public static String getIpAddress(Context context) {
        WifiManager manager = context.getSystemService(WifiManager.class);
        if (manager == null) {
            return "No Wifi";
        } else if (!manager.isWifiEnabled()) {
            return "Wifi is disabled";
        } else if (manager.getConnectionInfo() == null) {
            return "Wifi is not connected";
        } else {
            int a = manager.getConnectionInfo().getIpAddress();
            return (a & 0xFF) + "." + (a >> 8 & 0xFF) + "." + (a >> 16 & 0xFF) + "." + (a >> 24 & 0xFF);
        }
    }

    public static void setStoragePath(Context c) {
        path = c.getFilesDir();
    }

    public static File getStoragePath() {
        return path;
    }
}
