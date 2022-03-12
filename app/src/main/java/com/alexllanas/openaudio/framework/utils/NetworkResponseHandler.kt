package com.alexllanas.openaudio.framework.utils

import com.alexllanas.openaudio.framework.responses.SearchResponse
import com.alexllanas.common.state.ViewState
import com.alexllanas.common.state.ViewType
import com.alexllanas.common.utils.*
import com.alexllanas.common.utils.UIComponentType.Toast
import com.alexllanas.openaudio.presentation.state.HomeViewState

class NetworkResponseHandler : ResponseHandler<NetworkResponse> {
    override fun handleSuccessfulResponse(
        resource: Resource<NetworkResponse>,
        viewType: ViewType
    ): Resource<ViewState> {
        when (viewType) {
            is ViewType.Search -> {
                resource.data?.let { networkResponse ->
                    if (networkResponse is SearchResponse) {
                        return Resource.success(
                            HomeViewState(
                                resultTracks = networkResponse.results?.tracks ?: emptyList(),
                                resultPlaylists = networkResponse.results?.playlists ?: emptyList(),
                                resultUsers = networkResponse.results?.users ?: emptyList()
                            )
                        )
                    }
                }
            }
        }
        return Resource.success(ViewState())
    }

    override fun handleErrorResponse(
        resource: Resource<NetworkResponse>,
        viewType: ViewType
    ): Resource<ViewState> {
        when (viewType) {
            is ViewType.Search -> {
                return Resource.error(
                    data = HomeViewState(
                        error = NetworkError(
                            message = resource.message ?: "Unknown Error",
                            uiComponentType = Toast
                        )
                    ),
                    msg = resource.message ?: "Unknown Error"
                )
            }
        }
    }
}

















