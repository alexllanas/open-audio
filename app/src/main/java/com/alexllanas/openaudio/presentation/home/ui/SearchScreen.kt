package com.alexllanas.openaudio.presentation.home.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alexllanas.core.util.Constants.Companion.TAG
import com.alexllanas.openaudio.R
import com.alexllanas.openaudio.presentation.compose.components.BottomNav
import com.alexllanas.openaudio.presentation.compose.components.SearchResultTabLayout
import com.alexllanas.openaudio.presentation.home.state.*
import com.alexllanas.openaudio.presentation.main.state.MainViewModel
import com.alexllanas.openaudio.presentation.audio.state.MediaPlayerViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    playerViewModel: MediaPlayerViewModel,
    navHostController: NavHostController
) {
    Log.d(TAG, "SearchScreen: ${navHostController.currentBackStackEntry?.destination}")
    Scaffold(
        topBar = {

        },
        bottomBar = { BottomNav(navController = navHostController) }

    ) {
        SearchBarUI(homeViewModel, mainViewModel, playerViewModel, navHostController)
    }
}

@Composable
fun SearchBarUI(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    playerViewModel: MediaPlayerViewModel,
    navHostController: NavHostController
) {
    val homeState by homeViewModel.homeState.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(homeViewModel, mainViewModel, navHostController)
            when (homeState.searchDisplay) {
                SearchDisplay.Initial -> {
                    SearchResultTabLayout(
                        navHostController,
                        homeViewModel,
                        mainViewModel,
                        playerViewModel,
                        mainState
                    ) {
                        homeState.searchScreenState?.query?.let {
                            homeViewModel.dispatch(HomeAction.SearchAction(it))
                        }
                    }
                }
                SearchDisplay.Results -> {
                    SearchResultTabLayout(
                        navHostController,
                        homeViewModel,
                        mainViewModel,
                        playerViewModel,
                        mainState
                    ) {
                        homeState.searchScreenState?.query?.let {
                            homeViewModel.dispatch(HomeAction.SearchAction(it))
                        }
                    }
                }
                SearchDisplay.NoResults -> {
                    NoSearchResults()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    navHostController: NavHostController
) {
    var query by remember { mutableStateOf("") }
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    TopAppBar(
        title = { Text("Search") },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        actions = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .onFocusChanged { focusState ->
                        showClearButton = (focusState.isFocused)
                    }
                    .focusRequester(focusRequester),
                value = query,
                onValueChange = {
                    query = it
                    homeViewModel.dispatch(HomeAction.QueryTextChanged(it))
                    homeViewModel.dispatch(HomeAction.SearchAction(it))
                },
                placeholder = {
                    Text(text = stringResource(R.string.search_hint))
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent,
                    cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                ),
                leadingIcon = if (!showClearButton) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    }
                } else null,
                trailingIcon = {
                    AnimatedVisibility(
                        visible = showClearButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            query = ""
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(R.string.close)
                            )
                        }
                    }
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
            )
        }
    )
}


@Composable
fun NoSearchResults() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text("No matches found")
    }
}

