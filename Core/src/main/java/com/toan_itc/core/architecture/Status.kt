package com.toan_itc.core.architecture

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */
/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}