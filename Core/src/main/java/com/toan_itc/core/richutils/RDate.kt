@file:JvmName("RichUtils")
@file:JvmMultifileClass

package com.toan_itc.core.richutils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * format date with DateFormat string
 *
 * @param[format] optional. default is yyyy-MM-dd HH:mm:ss (2017-06-02 19:20:00)
 * @return Formatted Date
 */
@JvmOverloads
fun Date.asDateString(format: String? = "yyyy-MM-dd HH:mm:ss"): String = SimpleDateFormat(format, Locale.getDefault()).format(this)

/**
 * get readable string of given timestamp.
 *
 * @param[format] optional. default is yyyy-MM-dd HH:mm:ss (2017-06-02 19:20:00)
 * @return Formatted Date
 */
@JvmOverloads
fun Long.asDateString(format: String? = "yyyy-MM-dd HH:mm:ss"): String = Date(this).asDateString(format)

/**
 * parsing date from String
 *
 * @param[format] optional. default is yyyy-MM-dd HH:mm:ss (2017-06-02 19:20:00)
 * @return Date object, Nullable
 */
@JvmOverloads
fun String.parseDate(format: String? = "yyyy-MM-dd HH:mm:ss"): Date? = try {
    SimpleDateFormat(format, Locale.getDefault()).parse(this)
} catch (e: Exception) {
    null
}

/**
 * format formatted date to another formatted date
 *
 * @param[fromFormat] original date format
 * @param[toFormat] to convert
 * @return new Formatted Date
 */
fun String.toDateString(fromFormat: String, toFormat: String): String {
    val result: String
    val df = SimpleDateFormat(fromFormat, Locale.getDefault())
    val df2 = SimpleDateFormat(toFormat, Locale.getDefault())
    try {
        result = df2.format(df.parse(this))
    } catch (e: ParseException) {
        return this
    }
    return result
}

fun Long.getFriendlyTime(): String {
    val dateTime = Date(this * 1000)
    val sb = StringBuffer()
    val current = Calendar.getInstance().time
    var diffInSeconds = ((current.time - dateTime.time) / 1000).toInt()

    val sec = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val min = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val hrs = if (diffInSeconds >= 24) (diffInSeconds % 24) else diffInSeconds
    diffInSeconds /= 24
    val days = if (diffInSeconds >= 30) (diffInSeconds % 30) else diffInSeconds
    diffInSeconds /= 30
    val months = if (diffInSeconds >= 12) (diffInSeconds % 12) else diffInSeconds
    diffInSeconds /= 12
    val years = diffInSeconds

    if (years > 0) {
        if (years == 1) {
            sb.append("a year")
        } else {
            sb.append("$years years")
        }
        if (years <= 6 && months > 0) {
            if (months == 1) {
                sb.append(" and a month")
            } else {
                sb.append(" and $months months")
            }
        }
    } else if (months > 0) {
        if (months == 1) {
            sb.append("a month")
        } else {
            sb.append("$months months")
        }
        if (months <= 6 && days > 0) {
            if (days == 1) {
                sb.append(" and a day")
            } else {
                sb.append(" and $days days")
            }
        }
    } else if (days > 0) {
        if (days == 1) {
            sb.append("a day")
        } else {
            sb.append("$days days")
        }
        if (days <= 3 && hrs > 0) {
            if (hrs == 1) {
                sb.append(" and an hour")
            } else {
                sb.append(" and $hrs hours")
            }
        }
    } else if (hrs > 0) {
        if (hrs == 1) {
            sb.append("an hour")
        } else {
            sb.append("$hrs hours")
        }
        if (min > 1) {
            sb.append(" and $min minutes")
        }
    } else if (min > 0) {
        if (min == 1) {
            sb.append("a minute")
        } else {
            sb.append("$min minutes")
        }
        if (sec > 1) {
            sb.append(" and $sec seconds")
        }
    } else {
        if (sec <= 1) {
            sb.append("about a second")
        } else {
            sb.append("about $sec seconds")
        }
    }

    sb.append(" ago")

    return sb.toString()
}