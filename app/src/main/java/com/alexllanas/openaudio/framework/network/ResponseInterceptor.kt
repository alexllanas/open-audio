package com.alexllanas.openaudio.framework.network

import android.util.Log
import com.alexllanas.core.util.Constants
import com.alexllanas.core.util.Constants.Companion.TAG
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {
    var sessionToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val res = chain.proceed(req)
        sessionToken = res.getSessionToken()
//        val body = res.body()?.string()
        Log.d(TAG, "intercept: ${req.url()}")
        Log.d(TAG, "intercept: ${res.peekBody(Long.MAX_VALUE).string()}")
//        Log.d(Constants.TAG, "provideOkHttpClientBuilder: Session Token: $sessionToken")
        return res
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

