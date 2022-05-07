package com.flobiz.flobiztask.utils

import android.content.Context
import android.content.SharedPreferences
import com.flobiz.flobiztask.data.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object SharedPrefs {

    var sPref: SharedPreferences? = null
    var gson = Gson()
    val type: Type = object : TypeToken<List<Article?>>() {}.type

    fun init(context: Context) {
        sPref = context.getSharedPreferences(Constants.SHARED_PREFS_FILE, Context.MODE_PRIVATE).apply {
            val list = getString(Constants.ADS_POSITION_LIST, null)
            //if value is null means the value hasn't been commited
            if(list==null)
                sPref?.let { it.edit()?.putString(Constants.SHARED_PREFS_FILE, emptyList<Article>().toJson())?.apply() }
        }
    }

    fun saveList(list: List<Article?>) {
        sPref?.edit()?.putString(Constants.ADS_POSITION_LIST, list.toJson())?.apply()
    }

    fun getList(): MutableList<Article?> {
        var list = mutableListOf<Article?>()
        val value = sPref?.getString(Constants.ADS_POSITION_LIST, "")
        if(value!!.isNotBlank()) {
            list = gson.fromJson(value, type)
        }
        return list

    }

}