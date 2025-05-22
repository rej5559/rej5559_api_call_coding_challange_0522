package com.athenafriday.myapplication.data.util

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val errorMessage: String, val errorCode: Int? = null) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}