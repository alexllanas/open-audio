package com.alexllanas.openaudio.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexllanas.core.interactors.StreamIntent
import com.alexllanas.openaudio.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge

class StreamFragment : Fragment() {

    // private val adapter = TrackAdapter(mutableListOf())
    // private val viewModel : HomeViewModel by activityViewModels


    fun intents() : Flow<StreamIntent> = merge(
        initialIntent()
    )

    private fun initialIntent(): Flow<StreamIntent> = flowOf(StreamIntent.Initial)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // recyclerview.layoutManager = LinearLayoutManager(this)
        // recyclerview.adapter = adapter

        return inflater.inflate(R.layout.fragment_stream, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Initialize list with user stream tracks
        // bind()
    }

    private fun bind() {
        // observe the viewmodels state and direct it too the render function, so
        // viewmodel.states().observe{ state ->
        //      render(state)
        // }
        // then observe fragment's intents from viewmodel?
        // viewModel.processIntents(intents())
    }


    // render(state: HomeViewState) {
    //      progressBar.visible = state.isLoading
    //      if (state.stream.isEmpty() {
    //          recyclerView.visible = false
    //          emptyState.visible = true
    //      } else {
    //          recyclerView.visible = true
    //          emptyState.visible = false
    //          adapter.updateTracks(state.tracks)
    //      }
    //
    //      if (state.error != null) {
    //          Toast.makeText(this, "Error loading tracks.", Toast.LENGTH_SHORT).show()
    //          Log.d(TAG, "Error loading tracks: ${state.error.localizedMessage}")
    // }

    // private fun getStreamIntent():
    //
    //
    //
    //
    //


}