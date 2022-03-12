package com.alexllanas.core.interactors.search

import com.alexllanas.common.state.ViewState
import com.alexllanas.common.state.ViewType
import com.alexllanas.common.utils.*
import com.alexllanas.common.utils.Status.Success
import com.alexllanas.core.data.CommonDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class Search(
    private val commonDataSource: CommonDataSource,
    private val errorHandler: ErrorHandler,
    private val responseHandler: ResponseHandler<NetworkResponse>
) {

    suspend operator fun invoke(query: String): Flow<Resource<ViewState>> = flow {
        emit(Resource.loading(null))

        val result = withContext(Dispatchers.IO) {
            try {
                kotlinx.coroutines.withTimeout(3000L) {
                    Resource.success(commonDataSource.search(query))
                }
            } catch (throwable: Throwable) {
                errorHandler.getError(throwable)
            }
        }

        when (result.status) {
            is Success -> {
                emit(responseHandler.handleSuccessfulResponse(result, ViewType.Search))
            }
            else -> {
                emit(responseHandler.handleErrorResponse(result, ViewType.Search))
            }
        }
    }
}