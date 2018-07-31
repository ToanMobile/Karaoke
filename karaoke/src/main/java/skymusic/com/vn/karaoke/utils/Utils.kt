package skymusic.com.vn.karaoke.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.RequestBody
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern


/**
 * Created by Toan.IT on 12/6/17.
 * Email:Huynhvantoan.itc@gmail.com
 */

fun returnBody(value: String): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), value)
}

fun getDeviceID(context: Context): String {
    var deviceID = ""
    deviceID = generateDeviceIDWithoutSIM(context)
    Logger.i("device ID$deviceID")
    return deviceID
}

@SuppressLint("HardwareIds", "PrivateApi")
private fun generateDeviceIDWithoutSIM(context: Context): String {
    var deviceID = ""

    val pseudoIMEI = ("35"
            + // we make this look like a valid IMEI

            Build.BOARD.length % 10 + Build.BRAND.length % 10
            + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10
            + Build.DISPLAY.length % 10 + Build.HOST.length % 10
            + Build.ID.length % 10 + Build.MANUFACTURER.length % 10
            + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
            + Build.TAGS.length % 10 + Build.TYPE.length % 10
            + Build.USER.length % 10) // 13 digits

    /**
     * Serial Number Since Android 2.3 (“Gingerbread”) this is available via
     * android.os.Build.SERIAL. Devices without telephony are required to report a unique device
     * ID here; some phones may do so also. Serial number can be identified for the devices such
     * as MIDs (Mobile Internet Devices) or PMPs (Portable Media Players) which are not having
     * telephony services. Device-Id as serial number is available by reading the System
     * Property Value “ro.serialno” To retrieve the serial number for using Device ID, please
     * refer to example code below.
     */
    var serialnum: String? = null

    try {
        val c = Class.forName("android.os.SystemProperties")
        val get = c.getMethod("get", String::class.java, String::class.java)
        serialnum = get.invoke(c, "ro.serialno", "unknown") as String
    } catch (ignored: Exception) {
    }

    /**
     * More specifically, Settings.Secure.ANDROID_ID. A 64-bit number (as a hex string) that is
     * randomly generated on the device's first boot and should remain constant for the lifetime
     * of the device (The value may change if a factory reset is performed on the device.)
     * ANDROID_ID seems a good choice for a unique device identifier. To retrieve the ANDROID_ID
     * for using Device ID, please refer to example code below Disadvantages: Not 100% reliable
     * of Android prior to 2.2 (“Froyo”) devices Also, there has been at least one
     * widely-observed bug in a popular handset from a major manufacturer, where every instance
     * has the same ANDROID_ID.
     */
    val ANDROID_ID = Settings.Secure.getString(context.contentResolver,
            Settings.Secure.ANDROID_ID)

    deviceID = md5(pseudoIMEI + serialnum + ANDROID_ID).toUpperCase()

    return deviceID
}

private fun md5(input: String): String {
    try {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(input.toByteArray())
        val number = BigInteger(1, messageDigest)
        var hashtext = number.toString(16)
        // Now we need to zero pad it if you actually want the full 32
        // chars.
        while (hashtext.length < 32) {
            hashtext = "0" + hashtext
        }
        return hashtext
    } catch (e: NoSuchAlgorithmException) {
        throw RuntimeException(e)
    }

}

private fun showDownloadApp(context: Context, appPackageName: String) {
    try {
        context.startActivity(Intent(
                Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
    } catch (ex: ActivityNotFoundException) {
        context.startActivity(Intent(
                Intent.ACTION_VIEW, Uri.parse(getLinkAppStoreByPackageName(appPackageName))))
    }

}

private fun getAppPackageNameFromStoreUrl(storeUrl: String): String {
    val pattern = Pattern.compile("id=([^&]+)")
    val matcher = pattern.matcher(storeUrl)
    return if (matcher.find()) matcher.group(1) else ""
}

private fun getLinkAppStoreByPackageName(packageName: String): String {
    return "https://play.google.com/store/apps/details?id=" + packageName
}