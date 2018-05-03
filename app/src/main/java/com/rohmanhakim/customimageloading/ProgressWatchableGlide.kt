package com.rohmanhakim.customimageloading

import android.content.Context
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import okhttp3.*
import okio.*
import java.io.IOException
import java.io.InputStream
import java.lang.Exception

class ProgressWatchableGlide {
    companion object {
        fun initAndLoad(context: Context,
                        url: String,
                        onUpdate: (bytesRead: Long, contentLength: Long) -> Unit,
                        onReady: () -> Unit,
                        onError: () -> Unit): DrawableRequestBuilder<GlideUrl> {
            Glide.get(context).initProgressListener(onUpdate)
            return Glide.with(context)
                    .load(GlideUrl(url))
                    .addProgressListener(context, onReady, onError)
        }

        fun Glide.initProgressListener(onUpdate: (bytesRead: Long, contentLength: Long) -> Unit) {
            val okhttp: OkHttpClient by lazy {
                OkHttpClient.Builder()
                        .addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain?): Response {
                                val response = chain?.proceed((chain.request()))
                                return response?.newBuilder()?.body(
                                        CustomResponseBody(response.body(), onUpdate)
                                )?.build()!!
                            }
                        }).build()
            }

            this.register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okhttp))
        }

        fun DrawableRequestBuilder<GlideUrl>.addProgressListener(
                context: Context,
                onReady: () -> Unit,
                onError: () -> Unit
        ): DrawableRequestBuilder<GlideUrl> {

            return this.listener(object : RequestListener<GlideUrl, GlideDrawable> {
                override fun onException(e: Exception?, model: GlideUrl?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                    onError()
                    return false
                }

                override fun onResourceReady(resource: GlideDrawable?, model: GlideUrl?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    Glide.get(context).register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
                    onReady()
                    return false
                }
            })
        }
    }

    class CustomResponseBody(
            val responseBody: ResponseBody,
            val onUpdate: (bytesRead: Long, contentLength: Long) -> Unit
    ) : ResponseBody() {
        private var bufferedSource: BufferedSource? = null

        override fun contentType(): MediaType {
            return responseBody.contentType()
        }

        override fun contentLength(): Long {
            return responseBody.contentLength()
        }

        override fun source(): BufferedSource? {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()))
            }
            return bufferedSource
        }

        fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                internal var totalBytesRead = 0L

                @Throws(IOException::class)
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != (-1).toLong()) bytesRead else 0
                    onUpdate(totalBytesRead, responseBody.contentLength())
                    return bytesRead
                }
            }
        }
    }
}