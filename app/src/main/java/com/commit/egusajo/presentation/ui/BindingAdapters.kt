package com.commit.egusajo.presentation.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.commit.egusajo.R


@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.icon_profile)
        .into(imageView)
}