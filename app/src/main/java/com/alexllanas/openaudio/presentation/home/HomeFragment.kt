package com.alexllanas.openaudio.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexllanas.common.utils.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    // private val adapter = TrackAdapter(mutableListOf())
    private val viewModel: HomeViewModel by activityViewModels()


    fun viewIntents(): Flow<StreamIntent> = merge(
        initialIntent()
    )

    private fun initialIntent(): Flow<StreamIntent> =
        flowOf(StreamIntent.Initial("a session token"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewIntents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)


        // recyclerview.layoutManager = LinearLayoutManager(this)
        // recyclerview.adapter = adapter

        return inflater.inflate(R.layout.fragment_stream, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.run {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { state ->
                        render(state)
                    }
                }
            }
        }
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


    private fun render(state: HomeViewState) {
        Log.d(TAG, "render: stream size=${state.stream.size}")
//          progressBar.visible = state.isLoading
//          if (state.stream.isEmpty() {
//              recyclerView.visible = false
//              emptyState.visible = true
//          } else {
//              recyclerView.visible = true
//              emptyState.visible = false
//              adapter.updateTracks(state.tracks)
//          }
//
//          if (state.error != null) {
//              Toast.makeText(this, "Error loading tracks.", Toast.LENGTH_SHORT).show()
//              Log.d(TAG, "Error loading tracks: ${state.error.localizedMessage}")
    }

    // private fun getStreamIntent():
    //
    //
    //
    //
    //


}