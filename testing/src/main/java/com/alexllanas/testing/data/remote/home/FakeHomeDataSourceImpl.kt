package com.alexllanas.testing.data.remote.home

import arrow.core.Either
import arrow.core.leftWiden
import arrow.core.right
import com.alexllanas.core.data.remote.home.HomeDataSource
import com.alexllanas.core.util.getResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeHomeDataSourceImpl(private val fakeHomeRemoteServiceImpl: FakeHomeRemoteServiceImpl) :
    HomeDataSource {
    override suspend fun search(query: String): Flow<Either<Throwable, HashMap<String, List<*>>>> =
        flow {
            val resultMap = fakeHomeRemoteServiceImpl.search(query)
            emit(resultMap)
        }.getResult()
}