package com.alexllanas.openaudio.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.alexllanas.core.domain.models.Track

fun sendTweet(track: Track, context: Context) {
    val message = "${track.title}\nhttps://openwhyd.org/c/${track.id}\nvia @open_whyd"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://twitter.com/intent/tweet?text=" + Uri.encode(message))
    context.startActivity(intent)
}