package com.dolezal.image.ui

import androidx.annotation.DrawableRes
import com.dolezal.image.*

class NetworkStateRenderer (
    private val dsl: RenderingDsl
) {
    fun render(state: NetworkState) {
        when (state) {
            NetworkLoading -> onLoading()
            NetworkSuccess -> onSuccess()
            is NetworkError -> onError(state.throwable)
        }
    }

    private fun onLoading() {
        dsl.apply {
            showProgressBar()
            hideErrorBanner()
            disableButton()
            showImage(R.drawable.loading_wheel)
            hideKeyboard()
        }
    }

    private fun onSuccess() {
        dsl.apply {
            hideProgressBar()
            hideErrorBanner()
            enableButton()
        }
    }

    private fun onError(throwable: Throwable) {
        dsl.apply {
            hideProgressBar()
            showErrorBanner(text = throwable.message ?: throwable::class.java.simpleName)
            enableButton()
            hideImage()
        }
    }

    interface RenderingDsl {

        fun hideProgressBar()
        fun showProgressBar()

        fun hideErrorBanner()
        fun showErrorBanner(text: String)

        fun disableButton()
        fun enableButton()

        fun hideImage()
        fun showImage(@DrawableRes resId: Int)

        fun hideKeyboard()
    }
}