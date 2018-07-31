package com.toan_itc.core.kotlinify.support

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Created by gilgoldzweig on 12/09/2017.
 */
@SuppressLint("ValidFragment")
class FragmentDSL(@LayoutRes private val layoutRes: Int): Fragment() {

    var onViewCreated: ((view: View, context: Context, savedInstanceState: Bundle?) -> Unit)? = null
    fun onViewCreated(onViewCreated: (view: View, context: Context, savedInstanceState: Bundle?) -> Unit) {
        this.onViewCreated = onViewCreated
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.let {
            context?.let { it1 -> onViewCreated?.invoke(it, it1, savedInstanceState) }
        }

    }
}
fun fragment(@LayoutRes layoutRes: Int, init: FragmentDSL.() -> Unit) =
    FragmentDSL(layoutRes).apply(init)
