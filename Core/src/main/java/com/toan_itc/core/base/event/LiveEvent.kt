package com.toan_itc.core.base.event

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


// source: https://github.com/googlesamples/android-architecture-components/issues/63
class LiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {
        // observe the internal MutableLiveData
        super.observe(lifecycleOwner, Observer { value ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(value)
            }
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        mPending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = null
    }
}
