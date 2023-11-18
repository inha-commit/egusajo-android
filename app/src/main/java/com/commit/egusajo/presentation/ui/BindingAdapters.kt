package com.commit.egusajo.presentation.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commit.egusajo.R
import com.commit.egusajo.presentation.InputState
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat


@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    if (url.isNotBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter("profileImgUrl")
fun bindProfileImg(imageView: ImageView, url: String) {
    if (url.isNotBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.icon_profile)
            .into(imageView)
    }
}

@BindingAdapter("helperMessage")
fun bindHelperMessage(view: TextInputLayout, inputState: InputState) {
    when (inputState) {
        is InputState.Success -> view.helperText = inputState.msg
        is InputState.Error -> view.error = inputState.msg
        else -> {
            view.helperText = ""
            view.error = ""
        }
    }
}


@BindingAdapter("list")
fun <T, VH : RecyclerView.ViewHolder> bindList(recyclerView: RecyclerView, list: List<T>?) {
    val adapter = recyclerView.adapter as ListAdapter<T, VH>
    if (list != null) adapter.submitList(list)
}

@BindingAdapter("price")
fun bindPrice(textView: TextView, price: Int) {
    val dec = DecimalFormat("#,###원")
    textView.text = dec.format(price)
}

@BindingAdapter("textVisibility")
fun <T> bindTextVisibility(textView: TextView, state: List<T>){
    if(state.isEmpty()) textView.visibility = View.VISIBLE
    else textView.visibility = View.GONE
}

