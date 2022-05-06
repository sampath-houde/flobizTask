package com.zocket.flobiztask.api

import okhttp3.ResponseBody

sealed class ApiResponseHandler<out T> {

    data class Success<out T>(val value: T) : ApiResponseHandler<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ApiResponseHandler<Nothing>()

}