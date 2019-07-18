package com.dolezal.image.lib.cache

import android.graphics.Bitmap
import android.util.LruCache
import io.reactivex.Maybe

class MemoryImageCache(maxSize: Int) : ImageCache {

    private val lruCache = LruCache<String, Bitmap>(maxSize)

    override fun getImage(url: String): Maybe<Bitmap> {
        return lruCache[url]?.let { bitmap ->
            Maybe.just(bitmap)
        } ?: Maybe.empty()
    }

    override fun putImage(url: String, image: Bitmap) {
        lruCache.put(url, image)
    }
}