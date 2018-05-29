package com.toan_itc.core.utils

import android.view.View
import androidx.core.view.isGone
import java.util.*

/**
 * Created by ToanDev on 05/21/18.
 * Email: huynhvantoan.itc@gmail.com
 */
//View
fun View.OnClickListener.listenToViews(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

fun hideView(vararg views: View){
    views.forEach { it.isGone = true }
}

fun Date.isInFuture(): Boolean = this.time > System.currentTimeMillis()

//Check Emty
//region collections
fun <E> Collection<E>?.isNullOrEmpty(): Boolean = this == null || this.isEmpty()
fun <E> Collection<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion collections

//region maps
fun <K, V> Map<K, V>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <K, V> Map<K, V>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion maps

//region sets
fun <E> Set<E>?.isNullOrEmpty() = this == null || this.isEmpty()
fun <E> Set<E>?.isNotNullOrEmpty() = !isNullOrEmpty()
//endregion sets

//region lists
fun <E>List<E>?.isNullOrEmpty() = this == null || this.isEmpty()

fun <E>List<E>?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()