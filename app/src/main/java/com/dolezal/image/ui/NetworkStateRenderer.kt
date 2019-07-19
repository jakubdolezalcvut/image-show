package com.dolezal.image.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import com.dolezal.image.*
import com.google.android.material.snackbar.Snackbar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class NetworkStateRenderer(
    private val containerView: View,
    private val progressBar: MaterialProgressBar,
    private val button: Button,
    private val image: ImageView
) {
    private val context = containerView.context

    fun render(state: NetworkState) {
        Log.i(TAG, state.toString())

        when (state) {
            NetworkLoading -> onLoading()
            NetworkSuccess -> onSuccess()
            is NetworkError -> onError(state.throwable)
        }
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
        button.isEnabled = false
        image.visibility = View.VISIBLE
        image.setImageResource(R.drawable.loading_wheel)

        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(button.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun onSuccess() {
        progressBar.visibility = View.GONE
        button.isEnabled = true
    }

    private fun onError(throwable: Throwable) {
        progressBar.visibility = View.GONE
        button.isEnabled = true
        image.visibility = View.INVISIBLE

        val message = throwable.message ?: throwable::class.java.simpleName
        Snackbar.make(containerView, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "NetworkStateRenderer"
    }
}