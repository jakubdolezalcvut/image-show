package com.dolezal.image.vm

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolezal.image.NetworkError
import com.dolezal.image.NetworkLoading
import com.dolezal.image.NetworkState
import com.dolezal.image.NetworkSuccess
import com.dolezal.image.lib.ImageDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import toothpick.Scope

class ImageViewModel @RestrictTo(RestrictTo.Scope.TESTS) constructor(
    private val imageDataSource: ImageDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _networkState = MutableLiveData<NetworkState>()
    private val _image = MutableLiveData<Bitmap>()

    val networkState: LiveData<NetworkState> = _networkState
    val image: LiveData<Bitmap> = _image

    init {
        networkState.observeForever { state ->
            Log.i(TAG, state.toString())
        }
    }

    fun show() {
        image.value?.let { imageBitmap ->
            _image.postValue(imageBitmap)
        }
    }

    fun show(login: String, password: String) {
        _networkState.postValue(NetworkLoading)

        imageDataSource.getImage(login, password).subscribeBy(
            onSuccess = { imageBitmap ->
                _networkState.postValue(NetworkSuccess)
                _image.postValue(imageBitmap)
            },
            onError = { throwable ->
                _networkState.postValue(NetworkError(throwable))
            }
        ).also { disposable ->
            compositeDisposable.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "ImageViewModel"

        @SuppressLint("RestrictedApi")
        fun create(scope: Scope): ImageViewModel {
            return ImageViewModel(
                imageDataSource = scope.getDependency()
            )
        }
    }
}