package com.commit.egusajo.presentation.ui.intro.adapter

import android.widget.TextView

interface BankItemClickListener {
    fun onClick(view: TextView, bank: String)
}