package com.dolezal.image.lib

import android.graphics.Bitmap
import com.dolezal.image.lib.cache.ImageCache
import com.dolezal.image.lib.decoder.ImageDecoder
import com.dolezal.image.lib.net.ImageClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultImageDataSource @Inject constructor(
    private val imageClient: ImageClient,
    private val imageDecoder: ImageDecoder,
    private val imageCache: ImageCache
) : ImageDataSource {

    override fun getImage(login: String, password: String): Single<Bitmap> {
        val networkCall = imageClient.getImage(login, password)
            .subscribeOn(Schedulers.io())
            .map { payload ->
                imageDecoder.decode(payload)
            }
            .doOnSuccess { image ->
                imageCache.putImage(login, image)
            }
        return imageCache.getImage(login).switchIfEmpty(networkCall)
    }
}