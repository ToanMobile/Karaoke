package com.toan_itc.core.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class CoreBaseDataActivity<VM : ViewModel> : AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var viewModel: VM
    abstract fun getViewModel(): Class<VM>
    @LayoutRes
    abstract fun setLayoutResourceID(): Int

    protected abstract fun initViews()
    protected abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutResourceID())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        initViews()
        initData()
    }

}