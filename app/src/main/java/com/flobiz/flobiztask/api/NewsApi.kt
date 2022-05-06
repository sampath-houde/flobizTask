package com.flobiz.flobiztask.api

import com.flobiz.flobiztask.data.NewsResponse
import retrofit2.http.GET

interface NewsApi {

    @GET("everything?q=bitcoin&pageSize=50&apiKey=063ebbfefe5a4085931ea08ec4ddc5d7")
    suspend fun getNews() : NewsResponse
}