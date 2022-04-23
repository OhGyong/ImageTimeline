package com.android.imagerecordapp.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.imagerecordapp.R
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun bindImage(imgView: ImageView, imgUrl: String?){
        Glide.with(imgView.context)
            .load(imgUrl)
            .error(R.drawable.error)
            .into(imgView)
    }
}

