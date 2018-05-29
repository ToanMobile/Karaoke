package skymusic.com.vn.karaoke.utils

import android.app.Service
import android.content.Context
import android.os.Vibrator

object VibratorUtils {

    fun vibrate(context: Context, milliseconds: Long) {
        val vib = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(milliseconds)
    }

    fun vibrate(context: Context, pattern: LongArray, isRepeat: Boolean) {
        val vib = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(pattern, if (isRepeat) 1 else -1)
    }


} 