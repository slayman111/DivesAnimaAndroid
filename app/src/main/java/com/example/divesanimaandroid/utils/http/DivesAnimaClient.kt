package com.example.divesanimaandroid.utils.http

import android.content.Context
import com.example.divesanimaandroid.BuildConfig
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.models.dto.request.AuthRequest
import com.example.divesanimaandroid.models.dto.response.AuthResponse
import com.example.divesanimaandroid.utils.log.Logger
import com.example.divesanimaandroid.utils.toast.ToastUtil
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class DivesAnimaClient(private val context: Context) {

    private val log = Logger.getLogger(javaClass.name)

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var apiService: ApiService =
        retrofit.create(ApiService::class.java)

    suspend fun login(authRequest: AuthRequest): AuthResponse? =
        try {
            apiService.login(authRequest).await()
        } catch (t: Throwable) {
            log.error(t.message.toString())
            ToastUtil.show(context, context.getString(R.string.incorrect_login_or_password))
            null
        }
}
