package com.wk.mediate.present.extension

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wk.mediate.present.utils.DPIUtil

enum class ImageShape(val load: (ImageView, String, Int, Boolean, Int) -> Unit) {

    @SuppressLint("CheckResult")
    None({ imageView, url, _, transition, default ->
        val manager = Glide.with(imageView.context)
        val builder = manager.load(url)


        if( default != 0 ) {
            builder.error(default)
                .placeholder(default)
        }

        if( transition ) {
            builder.transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
        }

        builder.into(imageView)
    }),
    @SuppressLint("CheckResult")
    Circle({ imageView, url, _, transition, default ->
        val manager = Glide.with(imageView.context)

        val builder = manager.load(url).circleCrop()

        if( default != 0 ) {
            builder.error(default)
                .placeholder(default)
        }

        if( transition ) {
            builder.transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
        }
        builder.into(imageView)
    }),
    @SuppressLint("CheckResult")
    CornerRounded({ imageView, url, value, transition, default ->
        val manager = com.bumptech.glide.Glide.with(imageView.context)
        val builder = manager
            .load(url)
            .transform(
                com.bumptech.glide.load.resource.bitmap.CenterCrop(),
                com.bumptech.glide.load.resource.bitmap.RoundedCorners(
                    DPIUtil.dp2px(value.toFloat()).toInt()
                )
            )
        if( default != 0 ) {
            builder.error(default)
                .placeholder(default)
        }

        if( transition ) {
            builder.transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
        }
        builder.into(imageView)
    }),
}

fun ImageView.loadUrl(url: String? = "", shape: ImageShape = ImageShape.None, value: Int = 8, useTransition: Boolean = true, clear: Boolean = true, default: Int = 0) {
    if( clear ) {
        Glide.with(context).clear(this)
    }
    if (url.isNullOrEmpty()) {
        return
    }
    shape.load(this, url, value, useTransition, default)
}

fun ImageView.setDrawableImage(@DrawableRes resource: Int, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(resource)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}

fun ImageView.setLocalImage(uri: Uri, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(uri)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}