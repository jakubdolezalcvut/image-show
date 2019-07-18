package com.dolezal.image.lib

import com.dolezal.image.lib.cache.ImageCache
import com.dolezal.image.lib.cache.MemoryImageCache
import com.dolezal.image.lib.decoder.Base64ImageDecoder
import com.dolezal.image.lib.decoder.ImageDecoder
import com.dolezal.image.lib.net.ImageClient
import com.dolezal.image.lib.net.OkHttpImageClient
import com.dolezal.image.lib.net.PasswordEncryptor
import com.dolezal.image.lib.net.Sha1PasswordEncryptor
import toothpick.config.Module

@ExperimentalUnsignedTypes
object ImageToothpickModule : Module() {

    private const val MEMORY_CACHE_MAX_SIZE = 4

    init {
        bind(PasswordEncryptor::class.java) to Sha1PasswordEncryptor::class.java
        bind(ImageClient::class.java) to OkHttpImageClient::class.java
        bind(ImageDecoder::class.java) to Base64ImageDecoder::class.java
        bind(ImageCache::class.java).toInstance(MemoryImageCache(MEMORY_CACHE_MAX_SIZE))
        bind(ImageDataSource::class.java) to DefaultImageDataSource::class.java
    }
}