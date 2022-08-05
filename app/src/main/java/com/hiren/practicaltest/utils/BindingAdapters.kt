package com.hiren.practicaltest.utils

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.util.*

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

        /**
         * Load and set the image.
         *
         * @param view     Provide an reference of [SimpleDraweeView] class.
         * @param imageUrl Provide an `imageUrl` as [String].
         */
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: SimpleDraweeView, imageUrl: String?) {
            var uriPath: String? = ""
            if (!imageUrl.isNullOrEmpty()) {
                if (imageUrl.contains("http")) {
                    uriPath = imageUrl
                } else {
                    uriPath = "file://$imageUrl"
                }
            }
            view.setImageURI(uriPath)
        }

        fun FormattedfractionValue(amount: Double): String {
            return String.format(
                "%s%s",
                "$",
                fractionValue(amount)
            )
        }

        fun fractionValue(value: Double?): String {
            return String.format(Locale.getDefault(), "%.2f", value)
        }

        fun fractionValueInDouble(value: String?): Double {
            val retVal = try {
                java.lang.Double.valueOf(value ?: "")
            } catch (e: Exception) {
                e.printStackTrace()
                0.00
            }
            return try {
                java.lang.Double.valueOf(retVal)
            } catch (e: Exception) {
                e.printStackTrace()
                0.00
            }
        }

    }

}