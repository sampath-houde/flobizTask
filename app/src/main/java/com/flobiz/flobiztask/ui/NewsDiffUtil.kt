package com.flobiz.flobiztask.ui

import androidx.recyclerview.widget.DiffUtil
import com.flobiz.flobiztask.data.Article

class NewsDiffUtil(oldList: MutableList<Article?>, newList: MutableList<Article?>) : DiffUtil.Callback() {

    private val mOldNoteList: List<Article?> = oldList
    private val mNewNoteList: List<Article?> = newList

    override fun getOldListSize(): Int = mOldNoteList.size

    override fun getNewListSize(): Int = mNewNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList.get(oldItemPosition)?.url == mNewNoteList.get(
            newItemPosition)?.url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = mOldNoteList.get(oldItemPosition);
        val newNote = mNewNoteList.get(newItemPosition);

        return oldNote?.url == newNote?.url
    }
}