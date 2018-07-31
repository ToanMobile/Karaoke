@file:JvmName("RichUtils")
@file:JvmMultifileClass

package com.toan_itc.core.richutils

import android.view.View
import android.view.ViewGroup

/**
 * Convert ViewGroup's Children to List<Child>
 */
fun ViewGroup.convertChildrenList() : List<View> = (0 until childCount).map { getChildAt(it) }.toList()
