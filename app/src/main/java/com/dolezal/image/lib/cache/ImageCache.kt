package com.dolezal.image.lib.cache

import android.graphics.Bitmap
import io.reactivex.Maybe

interface ImageCache {
    fun getImage(url: String): Maybe<Bitmap>
    fun putImage(url: String, image: Bitmap)
}