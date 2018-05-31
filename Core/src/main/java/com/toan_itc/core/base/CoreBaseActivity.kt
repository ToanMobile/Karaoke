package com.toan_itc.core.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity

abstract class CoreBaseActivity : DaggerAppCompatActivity(){
    @LayoutRes
    protected abstract fun setLayoutResourceID(): Int
    protected abstract fun initViews()
    protected abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResourceID())
        initViews()
        initData()
    }
}