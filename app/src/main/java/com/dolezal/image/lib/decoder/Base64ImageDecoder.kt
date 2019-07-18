package com.dolezal.image.lib.decoder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import javax.inject.Singleton

@Singleton
class Base64ImageDecoder : ImageDecoder {

    override fun decode(payload: ByteArray): Bitmap {
        val binaryPayload = Base64.decode(payload, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(binaryPayload, 0, binaryPayload.size)
    }
}