package com.android.imagerecordapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.imagerecordapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(imgView: ImageView, imgUrl: String?){
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
            .error(R.drawable.error)
            .into(imgView)
    }
}

