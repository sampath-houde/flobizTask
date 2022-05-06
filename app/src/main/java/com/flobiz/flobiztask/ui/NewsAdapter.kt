package com.flobiz.flobiztask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.flobiz.flobiztask.data.Article
import com.zocket.flobiztask.R
import com.zocket.flobiztask.databinding.ViewNewsBinding


class NewsAdapter(private val list: List<Article>, private val mainActivity: MainActivity): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ViewNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

            //Load News Poster
            Glide.with(binding.newsPoster)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_baseline_search_24)
                .error(R.drawable.ic_baseline_error_24)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(binding.newsPoster)

            binding.newsTitle.text = article.title
            binding.newsAuthor.text = article.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(list[position]) {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = list.size

}