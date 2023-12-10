package com.example.divesanimaandroid.utils.http

import com.example.divesanimaandroid.models.dto.request.AuthRequest
import com.example.divesanimaandroid.models.dto.request.CreateTodoRequest
import com.example.divesanimaandroid.models.dto.response.ArticlePreviewResponse
import com.example.divesanimaandroid.models.dto.response.ArticleResponse
import com.example.divesanimaandroid.models.dto.response.AuthResponse
import com.example.divesanimaandroid.models.dto.response.DailyImageResponse
import com.example.divesanimaandroid.models.dto.response.IdPayloadRequest
import com.example.divesanimaandroid.models.dto.response.TodoItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body body: AuthRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body body: AuthRequest): Response<AuthResponse>

    @GET("article")
    suspend fun getArticlesPreview(): Response<List<ArticlePreviewResponse>>

    @GET("article/{id}")
    suspend fun getArticleById(@Path("id") id: Int): ArticleResponse

    @GET("daily-image")
    suspend fun getDailyImage(@Header("Authorization") authHeader: String): DailyImageResponse

    @GET("todo")
    suspend fun getTodos(@Header("Authorization") authHeader: String): List<TodoItemResponse>

    @PATCH("todo")
    suspend fun changeTodoCompleted(
        @Header("Authorization") authHeader: String,
        @Body id: IdPayloadRequest
    ): TodoItemResponse

    @POST("todo")
    suspend fun addTodo(
        @Header("Authorization") authHeader: String,
        @Body todo: CreateTodoRequest
    ): TodoItemResponse

    @DELETE("todo/{id}")
    suspend fun deleteTodo(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): TodoItemResponse

}