package com.alexllanas.core.domain.models

sealed class Error : Throwable() {
    data class NetworkError(val throwable: Throwable) : Error()
}
