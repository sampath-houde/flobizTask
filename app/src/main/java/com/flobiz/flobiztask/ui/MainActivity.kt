package com.flobiz.flobiztask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.flobiz.flobiztask.api.ApiResponseHandler
import com.flobiz.flobiztask.utils.Utils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.zocket.flobiztask.databinding.ActivityMainBinding
import com.zocket.flobiztask.databinding.ViewAdBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: NewsViewModel? = null
    private val adRequest by lazy { AdRequest.Builder().build() }
    private val adapter by lazy { NewsAdapter(::loadAd) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = Utils.getMainViewModel(this)
        callApi()

        binding.swipeRefresh.setOnRefreshListener {
            callApi()
        }
    }


    private fun callApi() {

        binding.swipeRefresh.isRefreshing = false
        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        binding.progressBar2.visibility = View.VISIBLE
        viewModel?.getNews()
        viewModel?.newsList?.observe(this) { response ->
            binding.progressBar2.visibility = View.GONE
            when (response) {
                is ApiResponseHandler.Success -> {
                    binding.banner.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.setData(response.value.articles)
                    binding.recyclerView.adapter = adapter
                }

                is ApiResponseHandler.Failure -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.banner.visibility = View.VISIBLE
                    if (response.isNetworkError)
                        Toast.makeText(applicationContext,
                        "Connection Error",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadAd(adBinding: ViewAdBinding?){
        adBinding?.adView?.loadAd(adRequest)
    }


}