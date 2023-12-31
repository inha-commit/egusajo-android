package com.commit.egusajo.config

import android.util.Log
import com.commit.egusajo.app.App.Companion.sharedPreferences
import com.commit.egusajo.util.Constants.TAG
import com.commit.egusajo.util.Constants.X_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AccessTokenInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwt: String? = sharedPreferences.getString(X_ACCESS_TOKEN, null)
        jwt?.let{
            Log.d("accessToken", it)
            builder.addHeader("access-token", jwt)
        }?:run{
            // 로컬에 저장된 토큰 없는경우
        }

        return chain.proceed(builder.build())
    }


}