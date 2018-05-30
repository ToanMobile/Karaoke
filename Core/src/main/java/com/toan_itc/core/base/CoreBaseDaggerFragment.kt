package com.toan_itc.core.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.base.di.Injectable
import com.toan_itc.core.binding.FragmentDataBindingComponent
import javax.inject.Inject

abstract class CoreBaseDaggerFragment<VM : BaseViewModel> : Fragment(), Injectable {
  /*  @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors*/
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    lateinit var viewModel: VM
    abstract fun getViewModel(): Class<VM>
    @LayoutRes
    abstract fun setLayoutResourceID(): Int
    abstract fun initData()
    abstract fun initView()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(setLayoutResourceID(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this,viewModelFactory).get(getViewModel())
        initView()
        initData()
    }

}