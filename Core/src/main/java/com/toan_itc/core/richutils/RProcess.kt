@file:JvmName("RichUtils")
@file:JvmMultifileClass

package com.toan_itc.core.richutils

import android.app.ActivityManager
import android.content.Context


/**
 * get boolean value for whatever application process is running
 *
 * @return true - FOREGROUND
 */
fun Context.isProcessRunning()
        = activityManager.runningAppProcesses.filter { it.processName.equals(packageName, true) }
        .map { it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND }
        .firstOrNull() ?: false