package com.dolezal.image

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.config.Module

object AppToothpickModule : Module() {

    init {
        bind(OkHttpClient::class.java).toInstance(getOkHttpClient())
    }

    private fun getOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().also { interceptor ->
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}