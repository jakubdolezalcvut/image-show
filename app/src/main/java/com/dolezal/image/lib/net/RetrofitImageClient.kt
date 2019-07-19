package com.dolezal.image.lib.net

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RetrofitImageClient @Inject constructor(
    private val passwordEncryptor: PasswordEncryptor,
    private val imageService: ImageService
) : ImageClient {

    override fun getImage(login: String, password: String): Single<ByteArray> {
        val encryptedPassword = passwordEncryptor.encrypt(password)
        val encodedBody = BODY_LOGIN + login

        return imageService.getImage(encryptedPassword, encodedBody)
            .subscribeOn(Schedulers.io())
            .map { dto ->
                dto.image.toByteArray()
            }
    }

    companion object {
        private const val BODY_LOGIN = "username="
    }
}