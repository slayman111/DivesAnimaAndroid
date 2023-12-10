package com.example.divesanimaandroid.utils.http

import android.content.Context
import com.example.divesanimaandroid.BuildConfig
import com.example.divesanimaandroid.exception.InvalidLoginOrPasswordException
import com.example.divesanimaandroid.exception.LoginAlreadyExistsException
import com.example.divesanimaandroid.models.dto.request.AuthRequest
import com.example.divesanimaandroid.models.dto.request.CreateTodoRequest
import com.example.divesanimaandroid.models.dto.response.ArticlePreviewResponse
import com.example.divesanimaandroid.models.dto.response.ArticleResponse
import com.example.divesanimaandroid.models.dto.response.AuthResponse
import com.example.divesanimaandroid.models.dto.response.DailyImageResponse
import com.example.divesanimaandroid.models.dto.response.IdPayloadRequest
import com.example.divesanimaandroid.models.dto.response.TodoItemResponse
import com.example.divesanimaandroid.utils.toast.ToastUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DivesAnimaClient(private val context: Context) {

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val apiService: ApiService =
        retrofit.create(ApiService::class.java)

    suspend fun login(authRequest: AuthRequest): AuthResponse? =
        try {
            val response = apiService.login(authRequest)

            if (response.code() == 401) throw InvalidLoginOrPasswordException.create()

            response.body()
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun register(authRequest: AuthRequest): AuthResponse? =
        try {
            val response = apiService.register(authRequest)

            if (response.code() == 409) throw LoginAlreadyExistsException.create()

            response.body()
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun getArticlesPreview(): List<ArticlePreviewResponse>? =
        try {
            val response = apiService.getArticlesPreview()

            response.body()
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun getArticleById(id: Int): ArticleResponse? =
        try {
            apiService.getArticleById(id)
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun getDailyImage(token: String): DailyImageResponse? =
        try {
            apiService.getDailyImage("Bearer $token")
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun getTodos(token: String): List<TodoItemResponse>? =
        try {
            apiService.getTodos("Bearer $token")
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun changeTodoCompleted(token: String, id: Int): TodoItemResponse? =
        try {
            apiService.changeTodoCompleted("Bearer $token", IdPayloadRequest(id))
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun addTodo(token: String, record: String): TodoItemResponse? =
        try {
            apiService.addTodo("Bearer $token", CreateTodoRequest(record))
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }

    suspend fun deleteTodo(token: String, id: Int): TodoItemResponse? =
        try {
            apiService.deleteTodo("Bearer $token", id)
        } catch (t: Throwable) {
            ToastUtil.show(context, t.message.toString())
            null
        }
}
