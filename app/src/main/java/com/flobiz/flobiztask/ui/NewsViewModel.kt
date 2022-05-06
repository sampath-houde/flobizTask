package com.flobiz.flobiztask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flobiz.flobiztask.data.NewsRepo
import com.flobiz.flobiztask.api.ApiResponseHandler
import com.flobiz.flobiztask.data.NewsResponse
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: NewsRepo): ViewModel() {

    private val _news_list: MutableLiveData<ApiResponseHandler<NewsResponse>> =
        MutableLiveData()

    val newsList: LiveData<ApiResponseHandler<NewsResponse>>
        get() = _news_list

    fun getNews() = viewModelScope.launch {
        _news_list.value = repository.getNews()
    }
}