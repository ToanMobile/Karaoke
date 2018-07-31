package com.toan_itc.core.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.toan_itc.core.base.event.Event
import com.toan_itc.core.base.event.LiveBus
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus


/**
 * Created by ToanDev on 11/30/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

abstract class BaseViewModel : ViewModel(), Observable {
    @Transient
    private var mObservableCallbacks: PropertyChangeRegistry? = null
    private val mLiveBus = LiveBus() //MainThread
    private var mCompositeDisposable: CompositeDisposable?=null

    override fun onCleared() {
        super.onCleared()
        Logger.d("onCleared")
        mCompositeDisposable?.clear()
    }

    fun getCompositeDisposable(): CompositeDisposable {
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable!!
    }

    @Synchronized override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        if (mObservableCallbacks == null) {
            mObservableCallbacks = PropertyChangeRegistry()
        }
        mObservableCallbacks?.add(callback)
    }

    @Synchronized override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        mObservableCallbacks?.let {
            mObservableCallbacks?.remove(callback)
        }
    }

    @Synchronized
    fun notifyChange() {
        mObservableCallbacks?.let {
            mObservableCallbacks?.notifyCallbacks(this, 0, null)
        }
    }

    fun notifyPropertyChanged(fieldId: Int) {
        mObservableCallbacks?.let {
            mObservableCallbacks?.notifyCallbacks(this, fieldId, null)
        }
    }

    fun <T : Event> observeEvent(lifecycleOwner: LifecycleOwner, eventClass: Class<T>, observer: Observer<T>) {
        mLiveBus.observe(lifecycleOwner, eventClass, observer)
    }

    fun <T : Event> sendEvent(event: T) {
        mLiveBus.send(event)
    }

    fun <T : Event> sendEventBus(event: T) {
        EventBus.getDefault().post(event)
    }
}
