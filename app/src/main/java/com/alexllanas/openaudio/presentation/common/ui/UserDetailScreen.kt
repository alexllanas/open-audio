package com.alexllanas.openaudio.presentation.common.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.lists.PlaylistList
import com.alexllanas.openaudio.presentation.home.state.HomeAction
import com.alexllanas.openaudio.presentation.home.state.HomeViewModel
import com.alexllanas.openaudio.presentation.main.state.MainState
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.main.ui.BottomNav
import com.alexllanas.openaudio.presentation.main.ui.navigateToPlaylistDetail
import com.alexllanas.openaudio.presentation.mappers.toUI
import com.alexllanas.openaudio.presentation.models.UserUIModel
import java.lang.IllegalArgumentException
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun UserDetailScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    mainState: MainState,
    navHostController: NavHostController,
    isCurrentUser: Boolean,
    onEditClick: () -> Unit,
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val context = LocalContext.current
    homeViewModel.clearSelectedPlaylist()
    Scaffold(topBar = {
        ConstraintLayout(
            Modifier.fillMaxWidth().height(height = 56.dp)
//                .background(MaterialTheme.colors.primary)
        ) {
            val (addIcon) = createRefs()
            if (isCurrentUser) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(R.string.settings),
                    modifier = Modifier
                        .alpha(0.8f)
                        .clickable {
                            navHostController.navigate("create_playlist")
                        }
                        .padding(end = 16.dp)
                        .constrainAs(addIcon) {
                            end.linkTo(parent.end)
                            centerVerticallyTo(parent)
                        }
                )
            }
        }
    },
        bottomBar = { BottomNav(navController = navHostController) }
    ) {
        if (isCurrentUser) {
            mainState.loggedInUser?.id?.let { id ->
                mainState.sessionToken?.let { token ->
                    homeViewModel.refreshSelectedUser(id, token)
                }
            }
        }
        var selectedUser = homeState.selectedUser


        selectedUser?.let { user ->
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                UserHeader(
                    user.toUI(),
                    homeViewModel = homeViewModel,
                    mainViewModel = mainViewModel,
                    mainState = mainState,
                    onEditClick = onEditClick,
                    isCurrentUser = isCurrentUser,
                    navHostController = navHostController,
                ) { isSubscribing ->
                    if (!isSubscribing) {
                        homeViewModel.dispatch(
                            HomeAction.FollowUser(
                                user,
                                mainState.sessionToken ?: throw IllegalArgumentException()
                            )

                        )
                        Toast.makeText(
                            context,
                            "Following ${user.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        homeViewModel.dispatch(
                            HomeAction.UnfollowUser(
                                user,
                                mainState.sessionToken ?: throw IllegalArgumentException()
                            )
                        )
                        Toast.makeText(
                            context,
                            "Unfollowed ${user.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    user.id?.let { id ->
                        homeViewModel.refreshSelectedUser(id, mainState.sessionToken)
                    }
                }
                Text(
                    "Playlists",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(8.dp)
                )
                PlaylistList(
                    user.playlists.sortedBy { it.name },
                    true
                ) { selectedPlaylist ->
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
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    mainState: MainState,
    isCurrentUser: Boolean,
    onEditClick: () -> Unit,
    navHostController: NavHostController,
    onFollowButtonClick: (Boolean) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(160.dp).clip(CircleShape)
                    .border(BorderStroke(1.dp, Color.Black), shape = CircleShape),
                model =
                URLDecoder.decode(
                    userUIModel.avatarUrl,
                    StandardCharsets.UTF_8.toString()
                ),
                contentDescription = null,
                placeholder = painterResource(R.drawable.blank_user),
                error = painterResource(R.drawable.blank_user),
                contentScale = ContentScale.Crop
            )
//            }
        }
        Text(
            text = userUIModel.name.toString(),
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(4.dp)
        )
        Row(Modifier.padding(vertical = 8.dp)) {
            Text(
                text = "${userUIModel.subscriptionCount} following",
                modifier = Modifier.clickable {
                    userUIModel.id?.let {
                        mainState.sessionToken?.let {
                            homeViewModel.dispatch(
                                HomeAction.GetFollowing(
                                    userUIModel.id,
                                    mainState.sessionToken
                                )
                            )
                        }
                    }
                    navHostController.navigate("follow/${Constants.FOLLOWING}")
                }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = "${userUIModel.subscriberCount} followers",
                modifier = Modifier.clickable {
                    userUIModel.id?.let {
                        mainState.sessionToken?.let {
                            homeViewModel.dispatch(
                                HomeAction.GetFollowers(
                                    userUIModel.id,
                                    mainState.sessionToken
                                )
                            )
                        }
                    }
                    navHostController.navigate("follow/${Constants.FOLLOWERS}")
                }
            )
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
