package com.alexllanas.core.interactors.home

import arrow.core.Either
import com.alexllanas.core.data.remote.home.HomeDataSource
import com.alexllanas.core.domain.models.GeneralError
import kotlinx.coroutines.flow.Flow

class Search(
    private val homeDataSource: HomeDataSource,
) {
    suspend operator fun invoke(query: String): Flow<Either<Throwable, HashMap<String, List<*>>>> =
        homeDataSource.search(query)
}