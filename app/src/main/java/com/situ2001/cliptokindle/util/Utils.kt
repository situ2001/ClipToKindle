package com.situ2001.cliptokindle.util

import android.content.Context
import android.net.wifi.WifiManager
import java.io.File

object Utils {
    private var path: File? = null

    /**
     * Get IP address oof Wifi
     * @param context
     * @return
     */
    fun getIpAddress(context: Context): String {
        val manager = context.getSystemService(WifiManager::class.java)
        return if (manager == null) {
            "No Wifi"
        } else if (!manager.isWifiEnabled) {
            "Wifi is disabled"
        } else if (manager.connectionInfo == null) {
            "Wifi is not connected"
        } else {
            val a = manager.connectionInfo.ipAddress
            (a and 0xFF).toString() + "." + (a shr 8 and 0xFF) + "." + (a shr 16 and 0xFF) + "." + (a shr 24 and 0xFF)
        }
    }

    fun setStoragePath(c: Context) {
        path = c.filesDir
    }

    fun getStoragePath(): File? {
        return path
    }
}