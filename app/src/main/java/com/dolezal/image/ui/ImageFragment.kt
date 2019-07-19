package com.dolezal.image.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dolezal.image.R
import com.dolezal.image.vm.ImageViewModel
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.include_login.*

class ImageFragment : Fragment() {

    private lateinit var imageViewModel: ImageViewModel
    private lateinit var loginValidator: LoginValidator
    private lateinit var networkStateRenderer: NetworkStateRenderer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginValidator = LoginValidator(resources)
        networkStateRenderer = NetworkStateRenderer(DefaultRenderingDsl())

        showBtn.setOnClickListener { _ ->
            loginValidator.validate(
                login = loginEditText.text.toString(),
                password = passwordEditText.text.toString(),
                onLoginError = { error ->
                    loginTextInput.error = error
                },
                onPasswordError = { error ->
                    passwordTextInput.error = error
                },
                onSuccess = { login, password ->
                    loginTextInput.error = null
                    passwordTextInput.error = null
                    imageViewModel.show(login, password)
                })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageViewModel = getViewModel { scope ->
            ImageViewModel.create(scope)
        }.apply {
            networkState.observe(viewLifecycleOwner, Observer { state ->
                networkStateRenderer.render(state)
            })
            image.observe(viewLifecycleOwner, Observer { imageBitmap ->
                imageView.setImageBitmap(imageBitmap)
                imageView.visibility = View.VISIBLE
            })
            show()
        }
    }

    inner class DefaultRenderingDsl : NetworkStateRenderer.RenderingDsl {

        override fun hideProgressBar() {
            progressBar.visibility = View.GONE
        }

        override fun showProgressBar() {
            progressBar.visibility = View.VISIBLE
        }

        override fun hideErrorBanner() {
            errorBanner.visibility = View.INVISIBLE
        }

        override fun showErrorBanner(text: String) {
            errorBanner.visibility = View.VISIBLE
            errorBanner.text = text
        }

        override fun disableButton() {
            showBtn.isEnabled = false
        }

        override fun enableButton() {
            showBtn.isEnabled = true
        }

        override fun hideImage() {
            imageView.visibility = View.INVISIBLE
        }

        override fun showImage(resId: Int) {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(resId)
        }

        override fun hideKeyboard() {
            val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(showBtn.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}