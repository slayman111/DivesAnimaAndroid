package com.example.divesanimaandroid.utils.bitmap

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

class BitmapConverter {

    companion object {
        fun bitmapFromStringByteArray(stringBytes: String): Bitmap? {
            val byteArray = Base64.decode(stringBytes, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }

}