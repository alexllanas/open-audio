package com.alexllanas.core.interactors.home

import arrow.core.Either
import com.alexllanas.core.data.remote.common.CommonDataSource
import com.alexllanas.core.domain.models.NetworkError
import kotlinx.coroutines.flow.Flow

class Search(
    private val commonDataSource: CommonDataSource,
) {
    suspend operator fun invoke(query: String): Flow<Either<NetworkError, HashMap<String, List<*>>>> =
        commonDataSource.search(query)
}