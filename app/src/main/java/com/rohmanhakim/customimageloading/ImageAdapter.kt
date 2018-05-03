package com.rohmanhakim.customimageloading

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_image.view.*


class ImageAdapter(val images: List<String> = mutableListOf<String>()) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        holder?.bind(images[position])
    }

}

class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(url: String) {
        loadImage(url)
    }

    fun loadImage(url: String) {
        itemView.progressBar.visibility = View.VISIBLE
        ProgressWatchableGlide
                .initAndLoad(itemView.context,
                        url,
                        { bytesRead, contentLength ->
                            val progress = ((bytesRead.toFloat() / contentLength.toFloat()) * 360).toInt()
                            itemView.progressBar.progress = progress
                        }, {
                    itemView.progressBar.visibility = View.GONE
                }, { e ->
                    Toast.makeText(itemView.context, "Error loading image: ${e.message}", Toast.LENGTH_SHORT).show()
                    itemView.progressBar.visibility = View.GONE
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.color.material_grey_300)
                .dontAnimate()
                .into(itemView.imageView)
    }
}