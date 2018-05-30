package com.toan_itc.core.base

import android.arch.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.toan_itc.core.base.event.Event
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus


/**
 * Created by ToanDev on 11/30/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

abstract class BaseViewModel : ViewModel() {
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

    fun <T : Event> sendEventBus(event: T) {
        EventBus.getDefault().post(event)
    }
}
