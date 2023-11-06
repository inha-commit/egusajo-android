package com.commit.egusajo.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

class EsBottomNavigationView @JvmOverloads constructor(
    context : Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BottomNavigationView(context, attrs, defStyleAttr) {

    init{
        val menuView = getChildAt(0) as ViewGroup
        menuView.getChildAt(1).isClickable = false
    }
}