package com.rohmanhakim.customimageloading

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rohmanhakim.customimageloading.api.BingImageService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recycler_view.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecyclerViewActivity : AppCompatActivity() {

    val adapter: ImageAdapter by lazy {
        ImageAdapter()
    }

    var loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl("https://www.bing.com/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    val bingImageService: BingImageService = retrofit.create(BingImageService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        initData()
    }

    fun initData() {
        recyclerView.adapter = adapter
        bingImageService.getImages("js", 0, 100, "en-US")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    recyclerView.adapter = ImageAdapter(it.images?.map { "https://www.bing.com/${it.url}" }.orEmpty())
                    recyclerView.adapter.notifyDataSetChanged()
                }, {
                    Toast.makeText(this, "Error when loading image: ${it.message}", LENGTH_SHORT).show()
                })
    }
}
