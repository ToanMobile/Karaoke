package com.toan_itc.core.richutils

import android.os.Build

/**
 * Created by ToanDev on 10/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

fun supportsKitKat(code: () -> Unit) {
    supportsVersion(code, 19)
}

fun supportsLollipop(code: () -> Unit) {
    supportsVersion(code, 21)
}

private fun supportsVersion(code: () -> Unit, sdk: Int) {
    if (Build.VERSION.SDK_INT >= sdk) {
        code.invoke()
    }
}