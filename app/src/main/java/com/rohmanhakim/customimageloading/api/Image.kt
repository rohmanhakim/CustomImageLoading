package com.rohmanhakim.customimageloading.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {

    @SerializedName("startdate")
    @Expose
    var startdate: String? = null
    @SerializedName("fullstartdate")
    @Expose
    var fullstartdate: String? = null
    @SerializedName("enddate")
    @Expose
    var enddate: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null
    @SerializedName("urlbase")
    @Expose
    var urlbase: String? = null
    @SerializedName("copyright")
    @Expose
    var copyright: String? = null
    @SerializedName("copyrightlink")
    @Expose
    var copyrightlink: String? = null
    @SerializedName("quiz")
    @Expose
    var quiz: String? = null
    @SerializedName("wp")
    @Expose
    var wp: Boolean = false
    @SerializedName("hsh")
    @Expose
    var hsh: String? = null
    @SerializedName("drk")
    @Expose
    var drk: Int = 0
    @SerializedName("top")
    @Expose
    var top: Int = 0
    @SerializedName("bot")
    @Expose
    var bot: Int = 0
    @SerializedName("hs")
    @Expose
    var hs: List<Any>? = null

}
