package com.alexllanas.core.util

import arrow.core.Either
import arrow.core.left
import arrow.core.leftWiden
import arrow.core.right
import com.alexllanas.core.domain.models.GeneralError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <R> Flow<R>.getResult(): Flow<Either<GeneralError.NetworkError, R>> =
    map {
        it.right().leftWiden<GeneralError.NetworkError, Nothing, R>()
    }.catch {
        emit(GeneralError.NetworkError.left())
    }