package com.commit.egusajo.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.commit.egusajo.databinding.DialogBirthdayDatepickerBinding

class BirthdayPickerDialog(
    context: Context,
    private val curYear: Int,
    private val curMonth: Int,
    private val curDay: Int,
    private val onConfirmBtnClickListener: (year: Int, month: Int, day: Int) -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogBirthdayDatepickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogBirthdayDatepickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            pickerMonth.minValue = 1
            pickerMonth.maxValue = 12
            pickerMonth.value = curMonth
            pickerYear.minValue = 1900
            pickerYear.maxValue = 2099
            pickerYear.value = curYear
            pickerDay.minValue = 1
            pickerDay.maxValue = 31
            pickerDay.value = curDay
        }

        setListener()
    }

    private fun setListener() {
        binding.btnCancel.setOnClickListener {
            this.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            onConfirmBtnClickListener(
                binding.pickerYear.value,
                binding.pickerMonth.value,
                binding.pickerDay.value
            )
            dismiss()
        }
    }

    override fun show() {
        if (!this.isShowing) super.show()
    }

}