package com.flobiz.flobiztask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.flobiz.flobiztask.data.Article
import com.zocket.flobiztask.R
import com.zocket.flobiztask.databinding.ViewAdBinding
import com.zocket.flobiztask.databinding.ViewNewsBinding
import kotlinx.coroutines.*


class NewsAdapter(
    val loadAd: (ViewAdBinding) -> Unit,
    val job: Job,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val AD_TYPE = 1
        private const val CONTENT = 0
    }

    private var list = mutableListOf<Article?>()

    fun setData(updatedList: MutableList<Article?>) {
        val diffCallback = NewsDiffUtil(list, updatedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }

    class MainViewHolder(itemView: View, val job: Job) : RecyclerView.ViewHolder(itemView) {

        var binding: ViewNewsBinding = ViewNewsBinding.bind(itemView)

        fun bind(article: Article) {
            binding.let {
                Glide.with(it.newsPoster)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.ic_baseline_search_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(it.newsPoster)

                it.newsTitle.text = article.title
                it.newsAuthor.text = article.author
            }
        }
    }

    class AdViewHolder(private val loadAd: (ViewAdBinding) -> Unit,itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ViewAdBinding = ViewAdBinding.bind(itemView)

        fun loadAd(position: Int, removeItem: (Int) -> Unit)  {
            loadAd(binding)
            binding.crossBtn.setOnClickListener {
                removeItem(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) AD_TYPE else CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == CONTENT) {
                val view = layoutInflater.inflate(R.layout.view_news, parent,false)
                MainViewHolder(view, job)
            } else {
                val view = layoutInflater.inflate(R.layout.view_ad, parent, false)
                AdViewHolder(loadAd,view)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                    if (itemViewType == CONTENT) {
                        this?.let { (holder as MainViewHolder).bind(it) }
                    } else {
                        (holder as AdViewHolder).apply {
                            loadAd(position, ::removeItem)
                        }
                    }

            }
        }
    }

    private fun removeItem(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

}