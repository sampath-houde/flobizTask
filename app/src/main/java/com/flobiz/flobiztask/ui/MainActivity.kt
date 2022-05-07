package com.flobiz.flobiztask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.flobiz.flobiztask.api.ApiResponseHandler
import com.flobiz.flobiztask.data.Article
import com.flobiz.flobiztask.utils.SharedPrefs
import com.flobiz.flobiztask.utils.Utils
import com.flobiz.flobiztask.utils.insertAdsToList
import com.flobiz.flobiztask.utils.toastShort
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.zocket.flobiztask.databinding.ActivityMainBinding
import com.zocket.flobiztask.databinding.ViewAdBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: NewsViewModel? = null
    private val adRequest by lazy { AdRequest.Builder().build() }
    private var adapter: NewsAdapter? = null
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        //Initialize Job when activity is created
        job = Job()



        launch(Dispatchers.Default) {
            MobileAds.initialize(this@MainActivity)
        }

        SharedPrefs.init(this@MainActivity)

        //Initialize Adapter
        adapter = NewsAdapter(::loadAd, job)
        binding.recyclerView.setHasFixedSize(true)

        //Initialize Viewmodel
        viewModel = Utils.getMainViewModel(this)

        if(SharedPrefs.getList().isEmpty()) callApi()
        else setValueToAdapter(SharedPrefs.getList())

        binding.swipeRefresh.setOnRefreshListener {
            callApi()
        }


        //observing changes on newsList
        viewModel?.newsList?.observe(this) { response ->
            binding.progressBar2.visibility = View.GONE
            when (response) {
                is ApiResponseHandler.Success -> {
                    val updatedList = (response.value.articles as MutableList<Article>).insertAdsToList()
                    setValueToAdapter(updatedList)
                }

                is ApiResponseHandler.Failure -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.banner.visibility = View.VISIBLE
                    if (response.isNetworkError)
                        toastShort("Connection Error")
                }
            }
        }
    }

    private fun setValueToAdapter(updatedList: MutableList<Article?>) {
        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        adapter?.setData(updatedList)
        binding.recyclerView.adapter = adapter
    }

    private fun callApi() {
        binding.swipeRefresh.isRefreshing = false
        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE

        binding.progressBar2.visibility = View.VISIBLE
        viewModel?.getNews()

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun loadAd(adBinding: ViewAdBinding?){
        launch(Dispatchers.Main + job) {
            adBinding?.adView?.loadAd(adRequest)
        }
    }

}