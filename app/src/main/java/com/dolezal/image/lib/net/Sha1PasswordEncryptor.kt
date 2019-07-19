package com.dolezal.image.lib.net

import java.security.MessageDigest
import javax.inject.Singleton

@Singleton
@ExperimentalUnsignedTypes
class Sha1PasswordEncryptor : PasswordEncryptor {

    private val messageDigest: MessageDigest by lazy {
        MessageDigest.getInstance(DIGEST_NAME)
    }

    override fun encrypt(password: String): String {
        val passwordBytes = password.toByteArray()
        val digestBytes = messageDigest.digest(passwordBytes)

        return digestBytes.toUByteArray().joinToString(FORMAT_SEPARATOR) { uByte ->
            FORMAT_HEXA.format(uByte.toInt())
        }
    }

    companion object {
        private const val DIGEST_NAME = "SHA-1"
        private const val FORMAT_SEPARATOR = ""
        private const val FORMAT_HEXA = "%02x"
    }
}