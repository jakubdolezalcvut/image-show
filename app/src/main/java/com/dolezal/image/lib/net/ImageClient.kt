package com.dolezal.image.lib.net

import io.reactivex.Single

interface ImageClient {
    fun getImage(url: String, login: String, password: String): Single<ByteArray>
}