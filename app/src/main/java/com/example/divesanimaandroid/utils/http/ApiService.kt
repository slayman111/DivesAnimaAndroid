package com.example.divesanimaandroid.utils.http

import com.example.divesanimaandroid.models.dto.request.AuthRequest
import com.example.divesanimaandroid.models.dto.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    fun register(@Body body: AuthRequest): Call<AuthResponse>

    @POST("auth/login")
    fun login(@Body body: AuthRequest): Call<AuthResponse>

}