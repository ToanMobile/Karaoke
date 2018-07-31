@file:JvmName("RichUtils")
@file:JvmMultifileClass

package com.toan_itc.core.richutils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * get network connection check
 *
 * if wifi is connected, will return 2
 * if mobile (3G, LTE) is connect, will return 1
 * else, return 0
 *
 * @return network state (check legend above)
 */
fun Context.checkNetwork(): Int {
    return when (connectivityManager.activeNetworkInfo.type) {
        ConnectivityManager.TYPE_WIFI -> 2
        ConnectivityManager.TYPE_MOBILE -> 1
        else -> 0
    }
}

@SuppressLint("MissingPermission")
fun isNetworkConnect(context: Context?): Boolean {
    val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}
