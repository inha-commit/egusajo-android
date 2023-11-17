package com.commit.egusajo.util

import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun showCalendarDatePicker(
    fragmentManager: FragmentManager,
    onSelectDateListener: (String) -> Unit
) {

    val calendarConstraintBuilder = CalendarConstraints.Builder()
    //오늘 이후만 선택가능하게 하는 코드
    calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())

    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("목표 날짜를 고르세요")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setCalendarConstraints(calendarConstraintBuilder.build())
        .build()
    datePicker.addOnPositiveButtonClickListener {
        onSelectDateListener(it.toDateString())
    }

    datePicker.show(fragmentManager, "")
}

private fun Long.toDateString(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = Date(this)
    return dateFormat.format(date)
}