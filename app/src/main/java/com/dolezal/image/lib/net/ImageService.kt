package com.dolezal.image.lib.net

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ImageService {
    @POST("download/bootcamp/image.php")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun getImage(@Header("Authorization") password: String, @Body login: String): Single<ImageDto>
}