package com.alexllanas.testing.interactors.upload

import com.alexllanas.core.interactors.upload.GetTrackMetadata
import com.alexllanas.core.util.Constants
import com.alexllanas.testing.data.remote.track.FakeTrackDataSourceImpl
import com.alexllanas.testing.data.remote.track.FakeTrackRemoteServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetTrackMetadataTest {

    private val remoteService = FakeTrackRemoteServiceImpl()
    private val dataSource = FakeTrackDataSourceImpl(remoteService)

    // system under test
    val getTrackMetadata = GetTrackMetadata(trackDataSource = dataSource)

    @Test
    fun get_metadata_success() = runTest {
        val resultFlow = getTrackMetadata(Constants.SAMPLE_MEDIA_URL)
        resultFlow
            .collect { result ->
                result.fold(
                    ifRight = {
                        assert(it.title == Constants.SAMPLE_TRACK_TITLE)
                        assert(it.thumbnailUrl == Constants.SAMPLE_TRACK_THUMBNAIL_URL)
                    },
                    ifLeft = {
                        assert(it.message == Constants.GET_TRACK_METADATA_ERROR)
                    }
                )
            }
    }

    @Test
    fun get_metadata_failure() = runTest {
        val resultFlow = getTrackMetadata(UUID.randomUUID().toString())
        resultFlow
            .collect { result ->
                result.fold(
                    ifRight = {
                        assert(it.title == Constants.SAMPLE_TRACK_TITLE)
                        assert(it.thumbnailUrl == Constants.SAMPLE_TRACK_THUMBNAIL_URL)
                    },
                    ifLeft = {
                        assert(it.message == Constants.GET_TRACK_METADATA_ERROR)
                    }
                )
            }
    }
}