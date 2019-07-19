package com.dolezal.image.ui

import android.content.res.Resources
import com.dolezal.image.R

class LoginValidator(
    private val resources: Resources
) {
    fun validate(
        login: String,
        password: String,
        onLoginError: (String) -> Unit = {},
        onPasswordError: (String) -> Unit = {},
        onSuccess: (String, String) -> Unit = { _, _ ->}
    ) {
        val isLoginValid = login.isNotBlank()
        val isPasswordValid = password.isNotBlank()

        if (!isLoginValid) {
            val error = resources.getString(R.string.validation_error_login)
            onLoginError(error)
        }
        if (!isPasswordValid) {
            val error = resources.getString(R.string.validation_error_password)
            onPasswordError(error)
        }
        if (isLoginValid && isPasswordValid) {
            onSuccess(login, password)
        }
    }
}