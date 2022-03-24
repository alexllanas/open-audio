package com.alexllanas.core.domain.models

sealed class GeneralError : Throwable() {
    object NetworkError : GeneralError()
    object ServerError : GeneralError()
    object UnknownError : GeneralError()
}