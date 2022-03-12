package com.alexllanas.common.utils

import com.alexllanas.common.state.ViewState
import com.alexllanas.common.state.ViewType

interface ResponseHandler<in T> {
    fun handleSuccessfulResponse(resource: Resource<T>, viewType: ViewType): Resource<ViewState>
    fun handleErrorResponse(resource: Resource<NetworkResponse>, viewType: ViewType): Resource<ViewState>
}

