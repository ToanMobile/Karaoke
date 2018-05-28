package com.toan_itc.core.architecture

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}