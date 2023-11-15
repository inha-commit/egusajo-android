package com.commit.egusajo.presentation.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commit.egusajo.R
import com.commit.egusajo.presentation.ui.intro.signup.SignupState
import com.commit.egusajo.presentation.ui.intro.signup.SignupUiState


@BindingAdapter("imgUrl")
fun bindImg(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .error(R.drawable.icon_profile)
        .into(imageView)
}

@BindingAdapter("warningText")
fun bindWarningText(view: TextView, state: SignupState) {
    when(state){
        is SignupState.Error -> {
            view.visibility = View.VISIBLE
            view.text = state.msg
        }
        is SignupState.Success -> view.visibility = View.GONE
        else -> {}
    }
}


@BindingAdapter("list")
fun <T, VH : RecyclerView.ViewHolder> bindList(recyclerView: RecyclerView, list: List<T>) {
    val adapter = recyclerView.adapter as ListAdapter<T, VH>
    adapter.submitList(list)
}