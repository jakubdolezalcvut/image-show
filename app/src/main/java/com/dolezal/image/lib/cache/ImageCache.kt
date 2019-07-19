package com.dolezal.image.lib.cache

import android.graphics.Bitmap
import io.reactivex.Maybe

interface ImageCache {
    fun getImage(key: String): Maybe<Bitmap>
    fun putImage(key: String, image: Bitmap)
}