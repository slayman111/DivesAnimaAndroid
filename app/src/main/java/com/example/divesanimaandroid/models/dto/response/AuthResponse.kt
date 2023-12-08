package com.example.divesanimaandroid.models.dto.response

data class AuthResponse(
    val login: String,
    val role: Role,
    val token: String
) {
    data class Role(
        val id: Int,
        val value: String
    )
}
