@file:JvmName("RichUtils")
@file:JvmMultifileClass

package com.toan_itc.core.richutils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * hide keyboard
 */
fun Activity.hideKeyboard() {
    this.currentFocus?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.showKeyboard() {
    this.currentFocus?.let {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

/**
 * hide keyboard
 */
fun Dialog.hideKeyboard() {
    this.currentFocus?.let {
        this.context.inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * toggle keyboard open / close
 */
fun Context.toggleKeyboard() {
    if (inputMethodManager.isActive) {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}