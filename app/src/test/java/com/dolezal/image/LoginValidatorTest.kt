package com.dolezal.image

import android.content.res.Resources
import com.dolezal.image.ui.LoginValidator
import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object LoginValidatorTest : Spek({

    val resources = mockk<Resources>(relaxed = true) {
        every { getString(R.string.validation_error_login) } returns "Login is mandatory"
        every { getString(R.string.validation_error_password) } returns "Please, type in a password"
    }
    val validator = spyk(LoginValidator(resources))

    lateinit var errorLoginLambda: (String) -> Unit
    lateinit var errorPasswordLambda: (String) -> Unit
    lateinit var successLambda: (String, String) -> Unit

    beforeEachTest {
        errorLoginLambda = spyk()
        errorPasswordLambda = spyk()
        successLambda = spyk()
    }
    describe("Missing login") {
        it("should call error") {
            validator.validate(
                login = "",
                password = "pass",
                onLoginError = errorLoginLambda,
                onPasswordError = errorPasswordLambda,
                onSuccess = successLambda
            )
            verify {
                errorLoginLambda("Login is mandatory")
                errorPasswordLambda wasNot Called
                successLambda wasNot Called
            }
        }
    }
    describe("Missing password") {
        it("should call error") {
            validator.validate(
                login = "pass",
                password = "",
                onLoginError = errorLoginLambda,
                onPasswordError = errorPasswordLambda,
                onSuccess = successLambda
            )
            verify {
                errorLoginLambda wasNot Called
                errorPasswordLambda("Please, type in a password")
                successLambda wasNot Called
            }
        }
    }
    describe("Missing login and password") {
        it("should call error") {
            validator.validate(
                login = "",
                password = "",
                onLoginError = errorLoginLambda,
                onPasswordError = errorPasswordLambda,
                onSuccess = successLambda
            )
            verify {
                errorLoginLambda("Login is mandatory")
                errorPasswordLambda("Please, type in a password")
                successLambda wasNot Called
            }
        }
    }
    describe("Login and password filled in") {
        it("should call success") {
            validator.validate(
                login = "login",
                password = "pass",
                onLoginError = errorLoginLambda,
                onPasswordError = errorPasswordLambda,
                onSuccess = successLambda
            )
            verify {
                errorLoginLambda wasNot Called
                errorPasswordLambda wasNot Called
                successLambda("login", "pass")
            }
        }
    }
})