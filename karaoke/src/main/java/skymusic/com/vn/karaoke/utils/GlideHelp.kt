package skymusic.com.vn.karaoke.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.request.target.Target
import skymusic.com.vn.karaoke.R

fun glideHelp(context: Context, url: String, imageView: ImageView) {
    GlideApp.with(context)
            .load("$url?img.contain=${imageView.width}x${imageView.height}")
            .dontAnimate()
            .into(imageView)
}

fun glideHelp(context: Context, url: String, size: Int, target: Target<Bitmap>) {
    GlideApp.with(context)
            .asBitmap()
            .load("$url?img.contain=${size}x$size")
            .override(size,size)
            .centerInside()
            .dontAnimate()
            .error(R.mipmap.ic_launcher)
            .into(target)
}

fun glideHelpDrawable(context: Context, drawable: Int, imageView: ImageView) {
    GlideApp.with(context)
            .load(drawable)
            .dontAnimate()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
}

