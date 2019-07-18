package com.dolezal.image

typealias NetworkLoading = NetworkState.Loading
typealias NetworkSuccess = NetworkState.Success
typealias NetworkError = NetworkState.Error

sealed class NetworkState {
    object Loading : NetworkState()
    object Success : NetworkState()
    data class Error(val throwable: Throwable) : NetworkState()
}