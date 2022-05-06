package com.zocket.flobiztask.data

import android.util.Log
import com.zocket.flobiztask.api.ApiResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : ApiResponseHandler<T> {
        return withContext(Dispatchers.IO) {
            try {
                ApiResponseHandler.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.d("exception", throwable.toString())
                when(throwable) {

                    is HttpException -> {
                        ApiResponseHandler.Failure(false,
                            throwable.code(),
                            throwable.response()?.errorBody())
                    }

                    else -> {
                        ApiResponseHandler.Failure(true, null, null)
                    }

                }
            }
        }
    }



}