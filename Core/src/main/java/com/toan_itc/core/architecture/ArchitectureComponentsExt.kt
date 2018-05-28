package com.toan_itc.core.architecture

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

//fun <T> LiveData<T>.observer(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer<T> { v -> observer.invoke(v) })

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>) = Transformations.switchMap(this, func)!!

fun <X, Y> LiveData<X>.map(func: (X) -> LiveData<Y>) = Transformations.map(this, func)

fun <T> LiveData<T>.observer(owner: LifecycleOwner, observer: (T) -> Unit) = this.observe(owner, Observer { it?.apply(observer) })