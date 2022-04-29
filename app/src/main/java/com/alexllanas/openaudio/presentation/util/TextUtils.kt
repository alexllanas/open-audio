package com.alexllanas.openaudio.presentation.util

import android.util.Patterns

fun CharSequence?.isValidEmail() =
    !isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()