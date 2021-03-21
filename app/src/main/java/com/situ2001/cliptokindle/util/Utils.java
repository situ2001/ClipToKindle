package com.situ2001.cliptokindle.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.wifi.WifiManager;

import java.io.File;

public class Utils {
    private static File storagePath;
    @SuppressLint("StaticFieldLeak")
    private static ContextWrapper contextWrapper;

    /**
     * Get wifi status by using a WifiManager
     * @param manager An WifiManager instance
     * @return 0, 1, 2 that indicate the Wifi status or a raw IP address
     */
    public static int getWifiStatus(WifiManager manager) {
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
     * @param a The value returned from getWifiStatus(Context context)
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

    /**
     * Set the path by using the Context passed into this method.
     * @param c A Context
     */
    public static void setStoragePath(Context c) {
        storagePath = c.getFilesDir();
    }

    /**
     * Getter for storagePath
     * @return The path of the directory holding application files.
     */
    public static File getStoragePath() {
        return storagePath;
    }

    public static void setContextWrapper(ContextWrapper c) {
        contextWrapper = c;
    }

    public static ContextWrapper getContext() {
        return contextWrapper;
    }
}
