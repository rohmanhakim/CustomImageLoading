package com.rohmanhakim.customimageloading.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BingImageService {

    @GET("HPImageArchive.aspx")
    fun getImages(@Query("format") format: String,
                  @Query("idx") idx: Int,
                  @Query("n") n: Int,
                  @Query("mkt") mkt: String): Single<ImagesResponse>
}