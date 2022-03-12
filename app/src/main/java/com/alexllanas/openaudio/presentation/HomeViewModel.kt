package com.alexllanas.openaudio.presentation

import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexllanas.core.interactors.search.Search
import com.alexllanas.openaudio.presentation.state.HomeViewState

class HomeViewModel(private val search: Search) {

    private val _viewState: MutableLiveData<HomeViewState> = MutableLiveData()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    fun handleNewData(data: HomeViewState) {
        val currentViewState = getViewState()
        data.let { newViewState ->
            currentViewState.resultTracks = newViewState.resultTracks
            currentViewState.resultPlaylists = newViewState.resultPlaylists
            currentViewState.resultUsers = newViewState.resultUsers
        }
        setViewState(currentViewState)
    }

    fun setQuery(query: String) {
        val currentViewState = getViewState()
        currentViewState.query = query
        setViewState(currentViewState)
    }

    private fun getViewState(): HomeViewState {
        return viewState.value ?: HomeViewState()
    }

    private fun setViewState(viewState: HomeViewState) {
        _viewState.value = viewState
    }
}