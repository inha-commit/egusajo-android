package com.commit.egusajo.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.commit.egusajo.util.LoadingDialog

abstract class BaseActivity<B : ViewDataBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
    private lateinit var loadingDialog : LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }


    fun showLoading(context : Context){
        loadingDialog = LoadingDialog(context)
        loadingDialog.show()
    }

    fun dismissLoading(){
        if(loadingDialog.isShowing){
            loadingDialog.dismiss()
        }
    }

    fun showCustomToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

}