package com.commit.egusajo.presentation.customview

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.commit.egusajo.R
import com.commit.egusajo.databinding.DialogCustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(
    view: View,
    private val text: String
) {

    companion object {

        fun make(view: View, text: String) = CustomSnackBar(view, text)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000).apply {
        anchorView = view
    }
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val binding: DialogCustomSnackbarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_custom_snackbar,
        null,
        false
    )

    init {
        initView()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            setPadding(40, 0, 40, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root, 0)
            binding.tvText.text = text
        }
    }

    fun show() {
        snackbar.show()
    }
}