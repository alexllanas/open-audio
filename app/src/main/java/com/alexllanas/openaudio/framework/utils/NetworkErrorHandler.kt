package com.alexllanas.openaudio.framework.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.alexllanas.common.utils.ErrorHandler
import com.alexllanas.common.utils.Resource
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

class NetworkErrorHandler(private val context: Context) : ErrorHandler {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun getError(throwable: Throwable): Resource<Nothing> {
        throwable.printStackTrace()

        if (!isConnectedToInternet(context)) {
            return Resource.error("503 No Internet Connection", null)
        }
        return when (throwable) {
            is TimeoutCancellationException -> {
                Resource.error("408 Request Timeout", null)
            }
            is IOException -> {
                Resource.error("A failed or interrupted I/O operation has occurred", null)
            }
            is HttpException -> {
                Resource.error("${throwable.code()} ${throwable.message()} ", null)
            }
            else -> {
                Resource.error("Unknown Error", null)
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnectedToInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        capabilities?.let {
            return (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }
        return false
    }
}