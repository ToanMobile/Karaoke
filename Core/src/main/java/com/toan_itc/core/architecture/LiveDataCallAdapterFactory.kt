package com.toan_itc.core.architecture

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by ToanDev on 28/2/18.
 * Email:Huynhvantoan.itc@gmail.com
 */

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}
