package com.toan_itc.core.kotlinify.permissions

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Created by gilgoldzweig on 04/09/2017.
 */
@SuppressLint("NewApi")
fun isVersionAbove(version: Int) = Build.VERSION.SDK_INT >= version

@TargetApi(Build.VERSION_CODES.M)
fun isNougatOrAbove() = isVersionAbove(Build.VERSION_CODES.N)

@TargetApi(Build.VERSION_CODES.M)
fun isMarshmallowOrAbove() = isVersionAbove(Build.VERSION_CODES.M)

fun isLollipopOrAbove() = isVersionAbove(Build.VERSION_CODES.LOLLIPOP)

fun isKikatOrBelow() = Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT

infix fun Context.isGranted(permission: String) =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
