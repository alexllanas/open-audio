package com.alexllanas.common.utils

/**
 * @see <a href="https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Status.kt">source</a>
 */
sealed class Status {
    object Success : Status()
    object Error : Status()
    object Loading : Status()
}