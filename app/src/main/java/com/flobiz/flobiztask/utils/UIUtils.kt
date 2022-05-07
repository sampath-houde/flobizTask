package com.flobiz.flobiztask.utils

import android.app.Activity
import android.widget.Toast
import com.flobiz.flobiztask.data.Article
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayDeque

fun Activity.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun<T> MutableList<T>.insertAdsToList(): MutableList<T?> {
    val updatedList = mutableListOf<T?>()

    this.forEachIndexed { index, t ->
        if(index % 4 == 2) {
            updatedList.add(null)
        } else {
            updatedList.add(t)
        }
    }

    return updatedList
}

fun<T> List<T>.toJson(): String{
    val gson = Gson()
    return gson.toJson(this)
}