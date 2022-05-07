package com.flobiz.flobiztask.data

import com.flobiz.flobiztask.api.NewsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class NewsRepo(
    private val api: NewsApi
) : BaseRepository(){

    suspend fun getNews() = safeApiCall {
        withContext(Dispatchers.IO) {
            api.getNews()
        }
    }
}