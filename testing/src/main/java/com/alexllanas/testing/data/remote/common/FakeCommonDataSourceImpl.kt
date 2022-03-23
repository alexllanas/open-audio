package com.alexllanas.testing.data.remote.common

import arrow.core.Either
import arrow.core.left
import arrow.core.leftWiden
import arrow.core.right
import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.core.domain.models.Error
import com.alexllanas.core.domain.models.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeCommonDataSourceImpl(private val fakeCommonRemoteServiceImpl: FakeCommonRemoteServiceImpl) :
    CommonDataSource {
    override suspend fun search(query: String): Flow<Either<Error.NetworkError, HashMap<String, List<*>>>> =
        flow {
            val resultMap = fakeCommonRemoteServiceImpl.search(query)
            emit(resultMap)
        }.map {
            it.right().leftWiden<Error.NetworkError, Nothing, HashMap<String, List<*>>>()
        }.catch {
            emit(Error.NetworkError(it).left())
        }
}