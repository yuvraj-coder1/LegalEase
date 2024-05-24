package com.example.legalease.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ClientBottomBarItem (
    val label: String,
    val icon: ImageVector,
    val route:String,
) {
    data object HomeScreen: ClientBottomBarItem(
        label = "Home",
        icon = Icons.Default.Home,
        route ="clientHomeScreen"
    )
    data object ProjectScreen: ClientBottomBarItem(
        label = "Find Projects",
        icon = Icons.Default.Search,
        route = "clientSearchScreen"
    )
    data object MessageListScreen : ClientBottomBarItem(
        label = "Messages",
        icon = Icons.Default.Message,
        route ="messageListScreen"
    )
    data object ProfileScreen: ClientBottomBarItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route ="clientProfileScreen"
    )
}

