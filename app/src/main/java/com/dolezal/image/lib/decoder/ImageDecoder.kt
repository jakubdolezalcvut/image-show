package com.dolezal.image.lib.decoder

import android.graphics.Bitmap

interface ImageDecoder {
    fun decode(payload: ByteArray): Bitmap
}