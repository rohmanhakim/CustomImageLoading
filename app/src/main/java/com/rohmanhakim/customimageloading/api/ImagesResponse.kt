package com.rohmanhakim.customimageloading.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImagesResponse {

    @SerializedName("images")
    @Expose
    var images: List<Image>? = null
    @SerializedName("tooltips")
    @Expose
    var tooltips: Tooltips? = null

}
