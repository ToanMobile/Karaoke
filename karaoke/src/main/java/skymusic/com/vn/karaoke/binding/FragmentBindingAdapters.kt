package skymusic.com.vn.karaoke.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import skymusic.com.vn.karaoke.R
import skymusic.com.vn.karaoke.utils.GlideApp
import javax.inject.Inject


/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters
@Inject constructor(val fragment: Fragment) {

    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        GlideApp.with(fragment.context!!).load(url).into(imageView)
    }
    @BindingAdapter("imageDrawable")
    fun bindImage(imageView: ImageView, drawable: Int) {
        GlideApp.with(fragment)
                .load(drawable)
                .dontAnimate()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
    }
}
