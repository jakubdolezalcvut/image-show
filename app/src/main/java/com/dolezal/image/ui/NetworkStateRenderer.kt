package com.dolezal.image.ui

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dolezal.image.*
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class NetworkStateRenderer(
    private val progressBar: MaterialProgressBar,
    private val errorBanner: TextView,
    private val button: Button,
    private val image: ImageView
) {
    private val context = progressBar.context

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
        errorBanner.visibility = View.INVISIBLE

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

        errorBanner.text = throwable.message ?: throwable::class.java.simpleName
        errorBanner.visibility = View.VISIBLE

        button.isEnabled = true
        image.visibility = View.INVISIBLE
    }

    companion object {
        private const val TAG = "NetworkStateRenderer"
    }
}