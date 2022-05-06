package com.flobiz.flobiztask.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flobiz.flobiztask.data.BaseRepository
import com.flobiz.flobiztask.data.NewsRepo
import com.flobiz.flobiztask.ui.NewsViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> NewsViewModel(repository as NewsRepo) as T
            else -> throw IllegalArgumentException("ViewModel class not found")
        }
    }

}