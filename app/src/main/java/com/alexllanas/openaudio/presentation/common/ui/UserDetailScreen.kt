package com.alexllanas.openaudio.presentation.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.alexllanas.core.domain.models.User
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.main.ui.navigateToPlaylistDetail
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.UserUIModel
import com.skydoves.landscapist.glide.GlideImage
import java.lang.IllegalArgumentException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun UserDetailScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    mainState: MainState,
    navHostController: NavHostController,
    isCurrentUser: Boolean,
    onEditClick: () -> Unit,
) {
    val homeState by homeViewModel.homeState.collectAsState()

    Scaffold(topBar = {
//        TopAppBar(
//            backgroundColor = MaterialTheme.colors.background
//        ) {
//            Row(
//                modifier = modifier.fillMaxWidth(),
//                horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
//            ) {
//                if (isCurrentUser) {
//                    Icon(
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .clickable {
//                                // TODO: dialog for creating new playlist
//                            },
//                        imageVector = Icons.Default.Add,
//                        contentDescription = stringResource(R.string.add_to_playlist)
//                    )
//                } else {
//                    Icon(
//                        modifier = Modifier.padding(8.dp)
//                            .clickable { },
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = stringResource(
//                            R.string.back_arrow
//                        )
//                    )
//                }
//            }
//        }
        ConstraintLayout(
            Modifier.fillMaxWidth().height(height = 56.dp).background(MaterialTheme.colors.primary)
        ) {
            val (gearIcon) = createRefs()

            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = stringResource(R.string.settings),
                modifier = Modifier
                    .alpha(0.8f)
                    .clickable {

                    }
                    .padding(end = 16.dp)
                    .constrainAs(gearIcon) {
                        end.linkTo(parent.end)
                        centerVerticallyTo(parent)
                    }
            )
        }
    },
        bottomBar = { BottomNav(navController = navHostController) }
    ) {
        if (isCurrentUser) {
            mainState.loggedInUser?.id?.let { id ->
                mainState.sessionToken?.let { token ->
                    homeViewModel.dispatch(HomeAction.GetUser(id, token))
                }
            }
//            currentUser = mainState.loggedInUser
        }
        var currentUser = homeState.selectedUser

        currentUser?.let { user ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                UserHeader(
                    user.toUI(),
                    onEditClick = onEditClick,
                    isCurrentUser = isCurrentUser
                ) { isSubscribing ->
                    if (!isSubscribing) {
                        homeViewModel.dispatch(
                            HomeAction.FollowUser(
                                user,
                                mainState.sessionToken ?: throw IllegalArgumentException()
                            )
                        )
                    } else {
                        homeViewModel.dispatch(
                            HomeAction.UnfollowUser(
                                user,
                                mainState.sessionToken ?: throw IllegalArgumentException()
                            )
                        )
                    }
                }
                Text(
                    "Playlists",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(8.dp)
                )
                PlaylistList(user.playlists) { selectedPlaylist ->
                    val newPlaylist = selectedPlaylist.copy()
                    newPlaylist.author = user
                    homeViewModel.dispatch(HomeAction.SelectPlaylist(newPlaylist))
                    navigateToPlaylistDetail(navHostController)
                }
            }
        }
    }
}


@Composable
fun UserHeader(
    userUIModel: UserUIModel,
    isCurrentUser: Boolean,
    onEditClick: () -> Unit,
    onFollowButtonClick: (Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        ) {
            GlideImage(
                modifier = Modifier.size(160.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(1.dp, Color.Black), shape = CircleShape),
                imageModel = URLDecoder.decode(
                    userUIModel.avatarUrl,
                    StandardCharsets.UTF_8.toString()
                ),
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(R.drawable.blank_user),
                error = ImageBitmap.imageResource(R.drawable.blank_user)
            )
        }
        Text(
            text = userUIModel.name.toString(),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(4.dp)
        )
        Row(Modifier.padding(vertical = 8.dp)) {
            Text(text = "${userUIModel.subscriptionCount} following")
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "${userUIModel.subscriberCount} followers")
        }
        if (isCurrentUser) {
            Button(
                onClick = { onEditClick() },
            ) {
                Text("EDIT")
            }
        } else {
            FollowButton(userUIModel, onFollowButtonClick)
        }
    }
}

@Composable
fun FollowButton(userUIModel: UserUIModel, onFollowButtonClick: (Boolean) -> Unit) {
    Button(
        {
            onFollowButtonClick(userUIModel.isSubscribing)
        },
    ) {
        if (userUIModel.isSubscribing)
            Text("FOLLOWING")
        else
            Text("FOLLOW")
    }
}
