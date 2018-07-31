package com.toan_itc.core.richutils

import android.app.Application
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ToanDev on 10/23/17.
 * Email:Huynhvantoan.itc@gmail.com
 */
class CrashUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        //单例工具类持有Application的弱引用
        private var mApplication: WeakReference<Application>? = null

        private var mInitialized: Boolean = false
        private var defaultDir: String? = null
        private var dir: String? = null

        private val FILE_SEP = System.getProperty("file.separator")
        private val FORMAT = SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault())

        private val CRASH_HEAD: String

        private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER: Thread.UncaughtExceptionHandler?
        private val UNCAUGHT_EXCEPTION_HANDLER: Thread.UncaughtExceptionHandler

        init {
            CRASH_HEAD = "\n************* Crash Log Head ****************" +
                    "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商

                    "\nDevice Model       : " + Build.MODEL +// 设备型号

                    "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本

                    "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本

                    "\n************* Crash Log Head ****************\n\n"

            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler()

            UNCAUGHT_EXCEPTION_HANDLER = Thread.UncaughtExceptionHandler { t, e ->
                if (e == null) {
                    android.os.Process.killProcess(android.os.Process.myPid())
                    System.exit(0)
                    return@UncaughtExceptionHandler
                }
                val now = Date(System.currentTimeMillis())
                val fileName = FORMAT.format(now) + ".txt"
                val fullPath = (if (dir == null) defaultDir else dir) + fileName
                if (!createOrExistsFile(fullPath))
                    return@UncaughtExceptionHandler
                Thread(Runnable {
                    var pw: PrintWriter? = null
                    try {
                        pw = PrintWriter(FileWriter(fullPath, false))
                        pw.write(CRASH_HEAD)
                        e.printStackTrace(pw)
                        var cause: Throwable? = e.cause
                        while (cause != null) {
                            cause.printStackTrace(pw)
                            cause = cause.cause
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } finally {
                        if (pw != null) {
                            pw.close()
                            System.exit(0)
                        }
                    }
                }).start()
                DEFAULT_UNCAUGHT_EXCEPTION_HANDLER?.uncaughtException(t, e)
            }
        }

        /**
         * 初始化
         *
         * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
         *
         * @return `true`: 初始化成功<br></br>`false`: 初始化失败
         */
        fun init(application: Application): Boolean {
            if (mApplication == null)
                mApplication = WeakReference(application)
            return init(application, "")
        }

        /**
         * 初始化
         *
         * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
         *
         * @param crashDir 崩溃文件存储目录
         * @return `true`: 初始化成功<br></br>`false`: 初始化失败
         */
        fun init(application: Application, crashDir: File): Boolean {
            if (mApplication == null)
                mApplication = WeakReference(application)
            return init(application, crashDir.absolutePath + FILE_SEP)
        }

        /**
         * 初始化
         *
         * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
         *
         * @param crashDir 崩溃文件存储目录
         * @return `true`: 初始化成功<br></br>`false`: 初始化失败
         */
        fun init(application: Application, crashDir: String): Boolean {
            if (mApplication == null)
                mApplication = WeakReference(application)
            if (isSpace(crashDir)) {
                dir = null
            } else {
                dir = if (crashDir.endsWith(FILE_SEP)) dir else dir!! + FILE_SEP
            }
            if (mInitialized)
                return true
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && mApplication!!.get()?.externalCacheDir != null)
                defaultDir = mApplication!!.get()?.externalCacheDir.toString() + FILE_SEP + "crash" + FILE_SEP
            else {
                defaultDir = mApplication!!.get()?.cacheDir.toString() + FILE_SEP + "crash" + FILE_SEP
            }
            Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER)
            mInitialized=true
            return mInitialized
        }

        private fun createOrExistsFile(filePath: String): Boolean {
            val file = File(filePath)
            if (file.exists())
                return file.isFile
            if (!createOrExistsDir(file.parentFile))
                return false
            try {
                return file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        }

        private fun createOrExistsDir(file: File?): Boolean {
            return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null)
                return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
