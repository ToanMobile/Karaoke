package com.toan_itc.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.base.di.Injectable
import javax.inject.Inject

abstract class CoreBaseDataFragment<VM : BaseViewModel> : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var viewModel: VM
    abstract fun getViewModel(): Class<VM>
    @LayoutRes
    abstract fun setLayoutResourceID(): Int
    abstract fun initData()
    abstract fun initView()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(getViewModel())
        initView()
        initData()
    }
}