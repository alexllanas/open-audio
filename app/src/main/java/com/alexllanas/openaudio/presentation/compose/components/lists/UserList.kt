package com.alexllanas.openaudio.presentation.compose.components.lists

import UserItem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
        itemsIndexed(users) { index, user ->
            UserItem(
                user = user,
                onUserClick = onUserClick,
                onFollowClick = onFollowClick,
            )
            if (index == users.size - 1)
                Spacer(Modifier.height(144.dp))
        }
    }
}