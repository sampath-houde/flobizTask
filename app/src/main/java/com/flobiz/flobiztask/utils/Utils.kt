package com.flobiz.flobiztask.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.flobiz.flobiztask.api.NewsApi
import com.flobiz.flobiztask.api.RetrofitInstance
import com.flobiz.flobiztask.data.NewsRepo
import com.flobiz.flobiztask.ui.MainActivity
import com.flobiz.flobiztask.ui.NewsViewModel

object Utils {
    fun getMainViewModel(context: Context) : NewsViewModel {
        val api = RetrofitInstance.buildApi(NewsApi::class.java)
        val repo = NewsRepo(api)
        val factory = ViewModelFactory(repo)
        return ViewModelProvider(context as MainActivity, factory).get(NewsViewModel::class.java)
    }
}

