package com.commit.egusajo.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.commit.egusajo.BuildConfig
import com.commit.egusajo.util.Constants.TAG
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {


    //  앱의 context 를 instance 변수에 저장
    init{
        instance =this
    }
    companion object{
        lateinit var instance : App
        lateinit var sharedPreferences: SharedPreferences

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences =
            applicationContext.getSharedPreferences("APP", MODE_PRIVATE)
        initKakaoLogin()
    }

    private fun initKakaoLogin(){
        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

}