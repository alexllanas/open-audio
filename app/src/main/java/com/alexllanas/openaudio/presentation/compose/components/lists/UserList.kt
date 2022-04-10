package com.alexllanas.openaudio.presentation.compose.components.lists

import UserItem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexllanas.core.domain.models.Track
import com.alexllanas.core.domain.models.User


@Composable
fun UserList(
    users: List<User>,
    onUserClick: (User) -> Unit = {},
    onFollowClick: (Boolean, User) -> Unit = { _: Boolean, _: User -> },
) {
    LazyColumn {
        items(users) { user ->
            UserItem(
                user = user,
                onUserClick = onUserClick,
                onFollowClick = onFollowClick,
            )
            if (users.last() == user)
                Spacer(Modifier.height(64.dp))
        }
    }
}