package com.dolezal.image.lib.net

interface PasswordEncryptor {
    fun encrypt(password: String): String
}