package com.rohmanhakim.customimageloading

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
                        "http://illinoisdouble.com/wp-content/uploads/photo-horizon-texture-aged-scene-weather-photo-free-download.jpg",
                        { bytesRead, contentLength ->
                            val progress = ((bytesRead.toFloat() / contentLength.toFloat()) * 360).toInt()
                            progressBar.progress = progress
                        }, {
                    progressBar.visibility = View.GONE
                }, { e ->
                    Toast.makeText(this, "Error loading image: ${e.message}", LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.color.material_grey_300)
                .dontAnimate()
                .into(imageView)
    }


}
