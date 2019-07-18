package com.dolezal.image.lib

import android.graphics.Bitmap
import io.reactivex.Single

interface ImageDataSource {
    fun getImage(login: String, password: String): Single<Bitmap>
}