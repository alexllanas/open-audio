package com.alexllanas.common.utils

import com.alexllanas.common.utils.Status.*

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 *
 * @see <a href="https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt">source</a>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Success, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Error, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Loading, data, null)
        }
    }

}
