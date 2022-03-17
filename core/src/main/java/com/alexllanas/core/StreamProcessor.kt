//package com.alexllanas.core
//
//import com.alexllanas.common.responses.NetworkResponse
//import com.alexllanas.core.data.remote.common.CommonDataSource
//import com.alexllanas.core.domain.Track
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//sealed class StreamProcessor(private val commonDataSource: CommonDataSource) {
//
//    abstract suspend fun process(): Flow<StreamResult>
//
//    object LoadStream : StreamProcessor() {
//        override suspend fun process(): Flow<StreamResult> =
//            combine<List<Track>, StreamResult>(
//            ) { tracks ->
//                StreamResult.StreamLoaded(tracks[0])
//            }.onStart {
//                emit(StreamResult.Loading)
//            }.catch {
//                emit(StreamResult.Error(it))
//            }
//    }
//
////    private suspend fun loadStream(): Flow<List<Track>> {
////        val result = commonDataSource.search("coco")
//////        return result.map { response ->
//////            when (response) {
//////            }
//////        }
////
////    }
//
//}
//
//sealed class StreamResult {
//    object Loading : StreamResult()
//
//    class StreamLoaded(tracks: List<Track>) : StreamResult()
//    class Error(error: Throwable) : StreamResult()
//
//
//}
