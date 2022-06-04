package com.alexllanas.testing.interactors.upload

import arrow.core.Const
import com.alexllanas.core.domain.models.TrackMetadata
import com.alexllanas.core.interactors.upload.GetTrackMetadata
import com.alexllanas.core.util.Constants
import com.alexllanas.testing.data.remote.track.FakeTrackDataSourceImpl
import com.alexllanas.testing.data.remote.track.FakeTrackRemoteServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.lang.Exception
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetTrackMetadataTest {

    /**
     * For requesting track metadata.
     */
    private val remoteService = FakeTrackRemoteServiceImpl()
    private val dataSource = FakeTrackDataSourceImpl(remoteService)

    /**
     * System under test
     */
    val getTrackMetadata = GetTrackMetadata(trackDataSource = dataSource)

    @Test
    fun get_metadata_success() = runTest {
        val resultFlow = getTrackMetadata(Constants.SAMPLE_MEDIA_URL)
        resultFlow
            .collect { result ->
                assert(result.title == Constants.SAMPLE_TRACK_TITLE)
                assert(result.thumbnailUrl == Constants.SAMPLE_TRACK_THUMBNAIL_URL)
            }
    }

    @Test
    fun get_metadata_failure() {
        val exception = assertThrows(Exception::class.java) {
            runTest {
                getTrackMetadata(UUID.randomUUID().toString())
            }
        }
        assertEquals(Constants.GET_TRACK_METADATA_ERROR, exception.message)
    }
}