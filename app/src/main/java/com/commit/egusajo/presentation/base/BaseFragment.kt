package com.commit.egusajo.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.commit.egusajo.util.BirthdayPickerDialog
import com.commit.egusajo.util.LoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {
    private var _binding: B? = null
    protected val binding get() = _binding!!

    private lateinit var loadingDialog : LoadingDialog
    private lateinit var birthdayPicker : BirthdayPickerDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
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

    fun showBirthdayPicker(
        context: Context,
        onConfirmBtnClickListener: (String) -> Unit
    ){
        birthdayPicker = BirthdayPickerDialog(context, onConfirmBtnClickListener)
        birthdayPicker.show()
    }

    fun dismissBirthdayPicker(){
        if(birthdayPicker.isShowing){
            birthdayPicker.dismiss()
        }
    }

    fun showCustomToast(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}