package com.situ2001.cliptokindle.util;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.File;

public class Utils {
    private static File path;

    public static int getWifiStatus(Context context) {
        WifiManager manager = context.getSystemService(WifiManager.class);
        if (manager == null) {
            return 0;
        } else if (!manager.isWifiEnabled()) {
            return 1;
        } else if (manager.getConnectionInfo() == null) {
            return 2;
        } else {
            return manager.getConnectionInfo().getIpAddress();
        }
    }

    /**
     * Get the hint of server status
     * @param a the value returned from getWifiStatus(Context context)
     * @return A String that indicate the status of server
     */
    public static String getHint(int a) {
        switch (a) {
            case 0: return "No Wifi";
            case 1: return "Wifi is disabled";
            case 2: return "Wifi is not connected";
            default: return "Listening on port 8080\n IP address: " +
                    (a & 0xFF) + "." + (a >> 8 & 0xFF) + "." +
                    (a >> 16 & 0xFF) + "." + (a >> 24 & 0xFF);
        }
    }

    public static void setStoragePath(Context c) {
        path = c.getFilesDir();
    }

    public static File getStoragePath() {
        return path;
    }
}
