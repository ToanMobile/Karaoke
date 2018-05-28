package com.toan_itc.core.binding

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import javax.inject.Inject


/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters
@Inject constructor(val fragment: Fragment) {
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(fragment).load(url).into(imageView)
    }
}
