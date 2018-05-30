package com.toan_itc.core.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class CoreBaseFragment : Fragment() {
    private var mContentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (null == mContentView) {
            mContentView = inflater.inflate(setLayoutResourceID(), container, false)
        }
        return mContentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initData()
    }

    protected abstract fun initViews()

    @LayoutRes
    protected abstract fun setLayoutResourceID(): Int

    protected abstract fun initData()
}
