package com.commit.egusajo.presentation.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.commit.egusajo.databinding.ActivityIntroBinding
import com.commit.egusajo.presentation.base.BaseActivity
import com.commit.egusajo.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {

    private val viewModel: IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObserver()
    }

    private fun initObserver(){
        repeatOnStarted {
            viewModel.events.collect{
                when(it){
                    is IntroEvents.GoToMainActivity -> startActivity(Intent(this@IntroActivity,MainActivity::class.java))
                    is IntroEvents.GoToGallery -> {}
                }
            }
        }
    }

}