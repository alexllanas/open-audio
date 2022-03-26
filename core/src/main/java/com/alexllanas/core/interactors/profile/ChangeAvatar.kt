package com.alexllanas.core.interactors.profile

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.alexllanas.core.data.remote.user.UserDataSource
import com.alexllanas.core.domain.models.User
import com.alexllanas.core.util.getResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

//upload image file -> change avatar
class ChangeAvatar(private val userDataSource: UserDataSource) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke(imageFilePath: String, sessionToken: String) = suspend {
        userDataSource
            .uploadAvatar(imageFilePath, sessionToken)
            .flatMapConcat { uploadedFilePath ->
                userDataSource.changeAvatar(uploadedFilePath, sessionToken).map {
                    it.fold(
                        ifLeft = { error -> error.left() },
                        ifRight = { user -> user.right() }
                    )
                }
            }
    }


}