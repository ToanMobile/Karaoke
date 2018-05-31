package com.toan_itc.core.binding

import android.support.v4.app.Fragment

/**
 * Created by Toan.IT on 30/5/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

/**
 * A Data Binding Component implementation for fragments.
 */
class FragmentDataBindingComponent(fragment: Fragment) : DataBindingComponent {
    private val adapter = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters() = adapter
}
