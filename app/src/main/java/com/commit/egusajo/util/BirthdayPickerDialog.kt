package com.commit.egusajo.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.commit.egusajo.databinding.DialogBirthdayDatepickerBinding
import java.time.LocalDate

class BirthdayPickerDialog(
    context: Context,
    private val onConfirmBtnClickListener: (birthday: String) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogBirthdayDatepickerBinding
    private val today = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogBirthdayDatepickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            pickerMonth.minValue = 1
            pickerMonth.maxValue = 12
            pickerMonth.value = today.monthValue
            pickerYear.minValue = 1900
            pickerYear.maxValue = 2099
            pickerYear.value = today.year
            pickerDay.minValue = 1
            pickerDay.maxValue = 31
            pickerDay.value = today.dayOfMonth
        }

        setListener()
    }

    private fun setListener() {
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            onConfirmBtnClickListener(
                binding.pickerYear.value.toString() + if (binding.pickerMonth.value < 10) "0${binding.pickerMonth.value}" else binding.pickerMonth.value.toString()
                        + if (binding.pickerDay.value < 10) "0${binding.pickerDay.value}" else binding.pickerDay.value.toString()
            )
            dismiss()
        }
    }

    override fun show() {
        if (!this.isShowing) super.show()
    }

}