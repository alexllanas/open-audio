package com.alexllanas.openaudio.framework.network

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.presentation.SESSION_TOKEN
import com.alexllanas.openaudio.presentation.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.Response


class ResponseInterceptor(private val context: Context) : Interceptor {
//    var sessionToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val res = chain.proceed(req)

        val url = req.url.toUrl().path
        Log.d(TAG, "intercept: $url")
        val sessionToken = res.getSessionToken()
        sessionToken?.let { token ->
            runBlocking {
                storeSessionToken("whydSid=$token")
            }
        }


//        val body = res.body()?.string()
//        Log.d(TAG, "intercept: ${req.url}")
//        Log.d(TAG, "intercept: ${res.peekBody(Long.MAX_VALUE).string()}")
//        Log.d(Constants.TAG, "provideOkHttpClientBuilder: Session Token: $sessionToken")
        return res
    }

    suspend fun storeSessionToken(token: String) {
        val x = token
        context.dataStore.edit { settings ->
            settings[SESSION_TOKEN] = token
        }
    }

    private fun Response.getSessionToken(): String? {
        val headers = headers("set-cookie")
        val cookies: List<String>
        val cookieMap: HashMap<String, String> = hashMapOf()
        if (headers.isNotEmpty()) {
            cookies = headers[0].split(";")
            cookies.forEach { pair ->
                val split = pair.split("=")
                if (split.size == 2) {
                    val (key, value) = split
                    cookieMap[key] = value
                }
            }
        }
        Log.d(TAG, "getSessionToken: ${cookieMap["whydSid"]}")
        return cookieMap["whydSid"]
    }
}

