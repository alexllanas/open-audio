package com.alexllanas.testing.interactors

import com.alexllanas.core.interactors.home.Search
import com.alexllanas.testing.NETWORK_ERROR_MESSAGE
import com.alexllanas.testing.SEARCH_MAP_RESULTS
import com.alexllanas.testing.data.remote.common.FakeCommonDataSourceImpl
import com.alexllanas.testing.data.remote.common.FakeCommonRemoteServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchTest {

    private val remoteService = FakeCommonRemoteServiceImpl()
    private val dataSource = FakeCommonDataSourceImpl(remoteService)

    // system under test
    val search = Search(commonDataSource = dataSource)

    @Test
    fun query_success() = runTest {
        query_success_error("succeed")
    }

    @Test
    fun query_error() = runTest {
        query_success_error("fail")
    }

    private fun query_success_error(succeedOrError: String) = runTest {
        val resultFlow = search(succeedOrError)
        resultFlow
            .collect { result ->
                result.fold(
                    ifRight = {
                        assert(it == SEARCH_MAP_RESULTS)
                    },
                    ifLeft = {
                        assert(it.throwable.message == NETWORK_ERROR_MESSAGE)
                    }
                )
            }
    }
}