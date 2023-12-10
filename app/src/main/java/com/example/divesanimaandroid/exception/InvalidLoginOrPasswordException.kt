package com.example.divesanimaandroid.exception

class InvalidLoginOrPasswordException private constructor(message: String?, cause: Throwable?) :
    RuntimeException(message, cause) {
    companion object {

        private const val MSG = "Неверный логин или пароль."

        fun create(cause: Throwable? = null) = InvalidLoginOrPasswordException(MSG, cause)
    }
}
