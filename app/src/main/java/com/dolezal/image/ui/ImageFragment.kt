package com.dolezal.image.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        networkStateRenderer = NetworkStateRenderer(
            containerView = coordinator,
            progressBar = progressBar,
            button = showBtn
        )
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
            })
            show()
        }
    }
}