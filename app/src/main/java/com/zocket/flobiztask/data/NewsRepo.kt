package com.task.newsapp.model

import com.zocket.flobiztask.data.BaseRepository
import com.zocket.flobiztask.api.NewsApi

class NewsRepo(
    private val api: NewsApi
) : BaseRepository(){

    suspend fun getNews() = safeApiCall {
        api.getNews()
    }
}