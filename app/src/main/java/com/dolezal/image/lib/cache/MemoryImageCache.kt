package com.dolezal.image.lib.cache

import android.graphics.Bitmap
import android.util.LruCache
import io.reactivex.Maybe

class MemoryImageCache(maxSize: Int) : ImageCache {

    private val lruCache = LruCache<String, Bitmap>(maxSize)

    override fun getImage(key: String): Maybe<Bitmap> {
        return lruCache[key]?.let { bitmap ->
            Maybe.just(bitmap)
        } ?: Maybe.empty()
    }

    override fun putImage(key: String, image: Bitmap) {
        lruCache.put(key, image)
    }
}