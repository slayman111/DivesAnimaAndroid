package com.example.divesanimaandroid.exception

class LoginAlreadyExistsException private constructor(message: String?, cause: Throwable?) :
    RuntimeException(message, cause) {
    companion object {

        private const val MSG = "Такой логин уже существует."

        fun create(cause: Throwable? = null) = LoginAlreadyExistsException(MSG, cause)
    }

}
