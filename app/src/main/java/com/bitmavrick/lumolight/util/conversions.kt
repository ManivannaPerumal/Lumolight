package com.bitmavrick.lumolight.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

@SuppressLint("DefaultLocale")
fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d : %02d", minutes, remainingSeconds)
}

fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}