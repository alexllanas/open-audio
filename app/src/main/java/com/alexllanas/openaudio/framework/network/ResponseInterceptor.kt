package com.alexllanas.openaudio.framework.network

import android.util.Log
import com.alexllanas.core.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val res = chain.proceed(req)
        Log.d(Constants.TAG, "provideOkHttpClientBuilder: ${res.peekBody(Long.MAX_VALUE).string()}")
        return res
    }
}