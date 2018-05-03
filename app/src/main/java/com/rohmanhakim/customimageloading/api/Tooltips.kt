package com.rohmanhakim.customimageloading.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tooltips {

    @SerializedName("loading")
    @Expose
    var loading: String? = null
    @SerializedName("previous")
    @Expose
    var previous: String? = null
    @SerializedName("next")
    @Expose
    var next: String? = null
    @SerializedName("walle")
    @Expose
    var walle: String? = null
    @SerializedName("walls")
    @Expose
    var walls: String? = null

}
