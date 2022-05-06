package com.zocket.flobiztask.api

import com.zocket.flobiztask.data.NewsResponse
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines?sources=techcrunch&apiKey=063ebbfefe5a4085931ea08ec4ddc5d7")
    suspend fun getNews() : NewsResponse
}