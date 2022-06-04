package com.alexllanas.testing.interactors.upload

import com.alexllanas.core.interactors.upload.GetUploadTrackResults
import com.alexllanas.core.util.Constants
import com.alexllanas.testing.data.remote.home.FakeHomeDataSourceImpl
import com.alexllanas.testing.data.remote.home.FakeHomeRemoteServiceImpl
import com.alexllanas.testing.data.remote.track.FakeTrackDataSourceImpl
import com.alexllanas.testing.data.remote.track.FakeTrackRemoteServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUploadTrackResultsTest {

    /**
     * For requesting track metadata.
     */
    private val trackRemoteService = FakeTrackRemoteServiceImpl()
    private val trackDataSource = FakeTrackDataSourceImpl(trackRemoteService)

    /**
     * For searching for existing tracks with track metadata.
     */
    private val homeRemoteService = FakeHomeRemoteServiceImpl()
    private val homeDataSource = FakeHomeDataSourceImpl(homeRemoteService)

    /**
     * System under test
     */
    private val getUploadTrackResults = GetUploadTrackResults(trackDataSource, homeDataSource)

    @Test
    fun get_upload_track_results_success() = runTest {
        val resultFlow = getUploadTrackResults(Constants.SAMPLE_MEDIA_URL)
        resultFlow
            .collect { result ->
                result.fold(
                    ifLeft = {},
                    ifRight = {

                    }
                )
            }
    }

    @Test
    fun get_upload_track_results_failure() = runTest {
        val resultFlow = getUploadTrackResults(Constants.SAMPLE_MEDIA_URL)
        resultFlow
            .collect { result ->
                result.fold(
                    ifLeft = { throwable ->
                        assert(throwable.message == Constants.SEARCHING_ERROR)
                    },
                    ifRight = {}
                )
            }
    }
}