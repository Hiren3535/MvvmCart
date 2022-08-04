package com.hiren.practicaltest.utils

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

class BindingAdapters {

    companion object {

        @JvmStatic
        val PROGRESS: String = "progress"

        @JvmStatic
        val NO_DATA: String = "no_data"

        @JvmStatic
        val CONTENT: String = "content"

        @JvmStatic
        @BindingAdapter("app:loadImage")
        fun setImageResource(view: SimpleDraweeView, imageUrl: String?) {
            val imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(imageUrl))
                .disableMemoryCache()
                .disableDiskCache()
                .build()
            view.setImageRequest(imageRequest)
        }
    }

}