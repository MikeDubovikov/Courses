package com.mdubovikov.common

sealed class Container<out T> {
    data object Pending : Container<Nothing>()
    data class Error(val exception: Exception) : Container<Nothing>()
    data class Success<T>(val value: T) : Container<T>()
}