package com.alexllanas.openaudio.presentation.util

import com.alexllanas.core.domain.models.Track

val SAMPLE_TRACKS = listOf<Track>(
    Track(
        "1",
        "the beatles - here comes the sun",
        userLikeIds = listOf(
            "1", "2", "3"
        )
    ),
    Track(
        "2",
        "drive - the cars",
        userLikeIds = listOf(
            "1", "2", "3"
        )
    ),
    Track(
        "3",
        "under my thumb - rolling stones",
        userLikeIds = listOf(
            "1", "2", "3"
        )
    )
)