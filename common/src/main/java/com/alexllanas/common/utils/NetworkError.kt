package com.alexllanas.common.utils

class NetworkError(
    val message: String = "",
    val uiComponentType: UIComponentType
)

sealed class UIComponentType {

    object Toast : UIComponentType()

    object Dialog : UIComponentType()

    object SnackBar : UIComponentType()

    object None : UIComponentType()
}
