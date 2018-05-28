package com.toan_itc.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.toan_itc.core.architecture.AppExecutors
import com.toan_itc.core.binding.FragmentDataBindingComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class CoreBaseDaggerActivity<VM : ViewModel> : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        initViews()
        initData()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}