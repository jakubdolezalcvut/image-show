package com.dolezal.image.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import com.dolezal.image.NetworkLoading
import com.dolezal.image.NetworkState
import com.dolezal.image.NetworkSuccess
import com.google.android.material.snackbar.Snackbar
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class NetworkStateRenderer(
    private val containerView: View,
    private val progressBar: MaterialProgressBar,
    private val button: Button
) {
    private val context = containerView.context

    fun render(state: NetworkState) {
        Log.i(TAG, state.toString())

        when (state) {
            NetworkLoading -> onLoading()
            NetworkSuccess -> onSuccess()
            is NetworkState.Error -> onError(state.throwable)
        }
    }

    private fun onLoading() {
        progressBar.visibility = View.VISIBLE
        button.isEnabled = false

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

        val message = throwable.message ?: throwable::class.java.simpleName
        Snackbar.make(containerView, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "NetworkStateRenderer"
    }
}