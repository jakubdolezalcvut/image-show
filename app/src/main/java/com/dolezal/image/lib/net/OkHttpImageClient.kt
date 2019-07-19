package com.dolezal.image.lib.net

import io.reactivex.Single
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpImageClient @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val passwordEncryptor: PasswordEncryptor
) : ImageClient {

    override fun getImage(login: String, password: String): Single<ByteArray> {
        val request = Request.Builder()
            .url(IMAGE_URL)
            .header(HEADER_AUTH, passwordEncryptor.encrypt(password))
            .post(getRequestBody(login))
            .build()

        return Single.create { emitter ->
            okHttpClient.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, ex: IOException) {
                    emitter.onError(ex)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()?.bytes()

                    when {
                        !response.isSuccessful -> emitter.onError(IOException("HTTP response returned code ${response.code()}"))
                        body == null -> emitter.onError(IOException("The body is empty!"))
                        else -> emitter.onSuccess(body)
                    }
                }
            })
        }
    }

    private fun getRequestBody(login: String): RequestBody {
        val contentType = MediaType.get(CONTENT_TYPE)
        val payload = BODY_LOGIN + login
        return RequestBody.create(contentType, payload.toByteArray())
    }

    companion object {
        private const val HEADER_AUTH = "Authorization"
        private const val BODY_LOGIN = "username="
        private const val CONTENT_TYPE = "application/x-www-form-urlencoded"
        private const val IMAGE_URL = "https://mobility.cleverlance.com/download/bootcamp/image.php"
    }
}