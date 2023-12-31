package com.commit.egusajo.config

import android.content.Intent
import android.util.Log
import com.commit.egusajo.BuildConfig.BASE_URL
import com.commit.egusajo.app.App
import com.commit.egusajo.app.App.Companion.context
import com.commit.egusajo.app.App.Companion.sharedPreferences
import com.commit.egusajo.data.model.ErrorResponse
import com.commit.egusajo.data.remote.RefreshApi
import com.commit.egusajo.presentation.ui.intro.IntroActivity
import com.commit.egusajo.util.Constants
import com.commit.egusajo.util.Constants.TAG
import com.commit.egusajo.util.Constants.X_ACCESS_TOKEN
import com.commit.egusajo.util.Constants.X_REFRESH_TOKEN
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class BearerInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        // API 통신중 특정코드 에러 발생 (accessToken 만료)
        if (response.code == 401) {

            var isRefreshed = false
            var accessToken = ""

            runBlocking {

                // 로컬에 refreshToken이 있다면
                sharedPreferences.getString(X_REFRESH_TOKEN, null)?.let { refresh ->

                    // refresh API 호출
                    val result = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(RefreshApi::class.java).refreshToken(refresh)

                    if (result.isSuccessful) {
                        result.body()?.let { body ->
                            Log.d(TAG,"refresh success")

                            // refresh 성공시 로컬에 저장
                            sharedPreferences.edit()
                                .putString(X_ACCESS_TOKEN, body.accessToken)
                                .apply()

                            isRefreshed = true
                            accessToken = body.accessToken
                        }
                    } else {
                        val error =
                            Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)

                        Log.d(TAG,error.message)
                        Log.d(TAG,error.description)
                    }
                }
            }

            if (isRefreshed) {

                // 기존 API 재호출
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
                response.close()

                return chain.proceed(newRequest)
            } else {
                // 해당 특정 에러코드가 그대로 내려간다면, IntroActivity로 이동. 세션 만료 처리
                sharedPreferences.edit()
                    .remove(X_ACCESS_TOKEN)
                    .remove(X_REFRESH_TOKEN)
                    .apply()

                val intent = Intent(context(), IntroActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context().startActivity(intent)
            }
        }
        
        return response
    }
}