package com.dolezal.image

import com.dolezal.image.ui.NetworkStateRenderer
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.IOException

object NetworkStateRendererTest : Spek({

    lateinit var dsl: TestRenderingDsl
    lateinit var renderer: NetworkStateRenderer

    beforeEachTest {
        dsl = TestRenderingDsl()
        renderer = NetworkStateRenderer(dsl)
    }
    describe("Loading") {
        it("should render") {
            dsl.isKeyboardVisible = true
            renderer.render(NetworkLoading)

            dsl.apply {
                isProgressBarVisible shouldBe true
                isErrorBannerVisible shouldBe false
                isButtonEnabled shouldBe false
                isImageVisible shouldBe true
                isKeyboardVisible shouldBe false
            }
        }
    }
    describe("Loading -> Success") {
        it("should render") {
            renderer.apply {
                render(NetworkLoading)
                render(NetworkSuccess)
            }
            dsl.apply {
                isProgressBarVisible shouldBe false
                isErrorBannerVisible shouldBe false
                isButtonEnabled shouldBe true
                isImageVisible shouldBe true
                isKeyboardVisible shouldBe false
            }
        }
    }
    describe("Loading -> Error") {
        it("should render") {
            renderer.apply {
                render(NetworkLoading)
                render(NetworkError(IOException("Message")))
            }
            dsl.apply {
                isProgressBarVisible shouldBe false
                isErrorBannerVisible shouldBe true
                errorBannerMessage shouldEqual "Message"
                isButtonEnabled shouldBe true
                isImageVisible shouldBe false
                isKeyboardVisible shouldBe false
            }
        }
    }

    describe("Loading -> Error -> Success") {
        it("should render") {
            renderer.apply {
                render(NetworkLoading)
                render(NetworkError(IOException("Message")))
                render(NetworkLoading)
                render(NetworkSuccess)
            }

            dsl.apply {
                isProgressBarVisible shouldBe false
                isErrorBannerVisible shouldBe false
                isButtonEnabled shouldBe true
                isImageVisible shouldBe true
                isKeyboardVisible shouldBe false
            }
        }
    }
})

private class TestRenderingDsl : NetworkStateRenderer.RenderingDsl {

    var isProgressBarVisible = false
    var isErrorBannerVisible = false
    var errorBannerMessage: String? = null
    var isButtonEnabled = true
    var isImageVisible = false
    var isKeyboardVisible = false

    override fun hideProgressBar() {
        isProgressBarVisible = false
    }

    override fun showProgressBar() {
        isProgressBarVisible = true
    }

    override fun hideErrorBanner() {
        isErrorBannerVisible = false
    }

    override fun showErrorBanner(text: String) {
        isErrorBannerVisible = true
        errorBannerMessage = text
    }

    override fun disableButton() {
        isButtonEnabled = false
    }

    override fun enableButton() {
        isButtonEnabled = true
    }

    override fun hideImage() {
        isImageVisible = false
    }

    override fun showImage(resId: Int) {
        isImageVisible = true
    }

    override fun hideKeyboard() {
        isKeyboardVisible = false
    }
}