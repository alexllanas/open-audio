//package com.alexllanas.core
//
//import com.alexllanas.core.interactors.StreamIntent
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//fun Flow<StreamIntent>.toProcessor(): Flow<StreamProcessor> = map { intent ->
//    when (intent) {
//        StreamIntent.Initial -> StreamProcessor.LoadStream
//    }
//
//}