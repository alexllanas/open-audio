package com.alexllanas.core.interactors.search

import com.alexllanas.core.data.remote.common.CommonDataSource

class Search(
    private val commonDataSource: CommonDataSource,
) {

    operator fun invoke(query: String) {
    }
}