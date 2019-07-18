package com.dolezal.image.lib

import android.graphics.Bitmap
import com.dolezal.image.lib.cache.ImageCache
import com.dolezal.image.lib.decoder.ImageDecoder
import com.dolezal.image.lib.net.ImageClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultImageDataSource @Inject constructor(
    private val imageClient: ImageClient,
    private val imageDecoder: ImageDecoder,
    private val imageCache: ImageCache
) : ImageDataSource {

    override fun getImage(login: String, password: String): Single<Bitmap> {
        val cacheCall = imageCache.getImage(IMAGE_URL).toSingle()

        val networkCall = imageClient.getImage(IMAGE_URL, login, password)
            .subscribeOn(Schedulers.io())
            .map { payload ->
                imageDecoder.decode(payload)
            }
            .doOnSuccess { image ->
                imageCache.putImage(IMAGE_URL, image)
            }
        return Single.concat(cacheCall, networkCall).firstOrError()
    }

    companion object {
        private const val IMAGE_URL = "https://mobility.cleverlance.com/download/bootcamp/image.php"
    }
}