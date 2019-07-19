package com.dolezal.image.lib

import com.dolezal.image.lib.cache.ImageCache
import com.dolezal.image.lib.cache.MemoryImageCache
import com.dolezal.image.lib.decoder.Base64ImageDecoder
import com.dolezal.image.lib.decoder.ImageDecoder
import com.dolezal.image.lib.net.*
import toothpick.config.Module

@ExperimentalUnsignedTypes
object ImageToothpickModule : Module() {

    private const val MEMORY_CACHE_MAX_SIZE = 4
    private const val BASE_URL = "https://mobility.cleverlance.com/"

    init {
        bind(PasswordEncryptor::class.java).to(Sha1PasswordEncryptor::class.java)
        bind(ImageService::class.java).toInstance(ImageServiceFactory.create(BASE_URL))
        bind(ImageClient::class.java).to(RetrofitImageClient::class.java)

        bind(ImageDecoder::class.java).to(Base64ImageDecoder::class.java)
        bind(ImageCache::class.java).toInstance(MemoryImageCache(MEMORY_CACHE_MAX_SIZE))
        bind(ImageDataSource::class.java).to(DefaultImageDataSource::class.java)
    }
}