package com.alexllanas.common.utils

interface ErrorHandler {
    fun getError(throwable: Throwable) : Resource<Nothing>
}