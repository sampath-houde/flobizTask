@file:Suppress("CAST_NEVER_SUCCEEDS")

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


class NewsAdapter(
    val loadAd: (ViewAdBinding)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val AD_TYPE = 1
        private const val CONTENT = 0
    }

    private var list = mutableListOf<Article>()

    fun setData(updatedList: List<Article>) {
        val diffCallback = NewsDiffUtil(list, updatedList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(updatedList)
        diffResult.dispatchUpdatesTo(this)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        fun loadAd() = loadAd(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 4 == 2) AD_TYPE else CONTENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == CONTENT) {
                val view = layoutInflater.inflate(R.layout.view_news, parent,false)
                MainViewHolder(view)
            } else {
                val view = layoutInflater.inflate(R.layout.view_ad, parent, false)
                AdViewHolder(loadAd,view)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                if (position % 4 != 2) {
                    (holder as MainViewHolder).bind(this)
                } else if (holder.itemViewType == AD_TYPE) {
                    (holder as AdViewHolder).apply {
                        loadAd()
                        binding.crossBtn.setOnClickListener {
                            notifyItemRemoved(position)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

}