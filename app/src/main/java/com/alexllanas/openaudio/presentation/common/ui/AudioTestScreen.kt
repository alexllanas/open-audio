package com.alexllanas.openaudio.presentation.common.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.framework.network.YoutubeApi
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.ServiceList.YouTube
import org.schabi.newpipe.extractor.downloader.Downloader
import org.schabi.newpipe.extractor.downloader.Request
import org.schabi.newpipe.extractor.downloader.Response
import org.schabi.newpipe.extractor.services.youtube.YoutubeService
import org.schabi.newpipe.extractor.services.youtube.extractors.YoutubeStreamExtractor
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory
import java.io.IOException
import javax.inject.Inject

@Composable
fun AudioTestScreen(mainViewModel: MainViewModel) {
    ExoPlayer(mainViewModel)
}


@Composable
fun ExoPlayer(mainViewModel: MainViewModel) {
    NewPipe.init(
        MyDownloader.init(
            OkHttpClient.Builder()
        )
    )
    val youTubeService = YoutubeService(0)
    val linkHandlerFactory = YoutubeStreamLinkHandlerFactory.getInstance()
    val video = linkHandlerFactory.fromUrl("https://www.youtube.com/watch?v=R_H5GxDTikM")
    val extractor = YoutubeStreamExtractor(youTubeService, video)
    extractor.fetchPage()
    val audioStreams = extractor.audioStreams
    Log.d(TAG, "ExoPlayer: ")


//    Column(modifier = Modifier.fillMaxSize()) {
//        AndroidView(
//            factory = { context ->
//                PlayerView(context).apply {
//                    player = player
//                }
//            }
//        )
//    }

}


@SuppressLint("RememberReturnType")
@Composable
fun ExoPlayer1() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Blue)
        ) {
            Text(
                text = "ExoPlayer Video",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        val exoPlayer = remember(context) {
            SimpleExoPlayer.Builder(context).build().apply {
                val dataSourceFactory: com.google.android.exoplayer2.upstream.DataSource.Factory =
                    DefaultDataSourceFactory(
                        context,
                        com.google.android.exoplayer2.util.Util.getUserAgent(
                            context,
                            context.packageName
                        )
                    )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri("https://www.youtube.com/watch?v=xo4lRv51Hf0&ab_channel=IzaeldeMouraPereira"))
                this.setMediaSource(source)
                this.prepare()
            }
        }

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                }

            }
        )
    }
}