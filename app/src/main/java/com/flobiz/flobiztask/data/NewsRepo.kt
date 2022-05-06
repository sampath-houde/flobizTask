package com.flobiz.flobiztask.data

import com.flobiz.flobiztask.api.NewsApi

class NewsRepo(
    private val api: NewsApi
) : BaseRepository(){

    suspend fun getNews() = safeApiCall {
        api.getNews()
    }
}