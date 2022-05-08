package com.alexllanas.openaudio.presentation.compose.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alexllanas.core.util.Constants
import com.alexllanas.openaudio.presentation.main.ui.NavItem

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        NavItem.Stream,
        NavItem.Search,
        NavItem.Upload,
        NavItem.Profile
    )
    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black
                    )
                )
            )
    ) {
        BottomNavigation(
            modifier = Modifier,
            backgroundColor = Color.Transparent,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title
                            )
                        }
                    },
                    label = { Text(text = item.title, fontSize = 9.sp) },
                    selectedContentColor = MaterialTheme.colors.onSurface,
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.6f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screenRoute,
                    onClick = {
                        Log.d(Constants.TAG, "BottomNav: ${navController.currentDestination}")
                        navController.navigate(item.screenRoute) {
                            navController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}