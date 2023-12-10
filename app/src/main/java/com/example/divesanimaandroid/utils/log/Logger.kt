package com.example.divesanimaandroid.utils.log

import android.util.Log

class Logger(private var tag: String) {

    companion object {
        fun getLogger(tag: Class<Any>) =
            Logger(tag.name)
    }

    fun info(message: String?) {
        Log.i(tag, message ?: "")
    }

    fun error(message: String?) {
        Log.e(tag, message ?: "")
    }

}