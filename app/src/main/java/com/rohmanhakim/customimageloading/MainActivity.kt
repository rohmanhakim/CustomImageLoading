package com.rohmanhakim.customimageloading

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImage()
    }

    fun loadImage() {
        progressBar.visibility = View.VISIBLE
        ProgressWatchableGlide
                .initAndLoad(this,
                        "https://lucasmccann.ca/wp-content/uploads/2017/06/pexels-photo-42379.jpg",
                        { bytesRead, contentLength ->
                            val progress = ((bytesRead.toFloat() / contentLength.toFloat()) * 100).toInt()
                            progressBar.progress = progress
                        }, {
                    progressBar.visibility = View.GONE
                }, {
                    Log.e("kektai", "error")
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.color.material_grey_300)
                .dontAnimate()
                .into(imageView)
    }


}
