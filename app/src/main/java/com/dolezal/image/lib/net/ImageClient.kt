package com.dolezal.image.lib.net

import io.reactivex.Single

interface ImageClient {
    fun getImage(login: String, password: String): Single<ByteArray>
}