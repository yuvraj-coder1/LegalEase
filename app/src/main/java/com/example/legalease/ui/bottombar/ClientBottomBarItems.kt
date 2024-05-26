package com.example.legalease.ui.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.legalease.ui.navigation.ChatListScreen
import com.example.legalease.ui.navigation.LawyerSearchScreen
import com.example.legalease.ui.navigation.SearchScreen

sealed class LawyerBottomBarItem (
    val label: String,
    val icon: ImageVector,
    val route:Any,
) {
    data object HomeScreen: LawyerBottomBarItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = com.example.legalease.ui.navigation.HomeScreen
    )
    data object ProjectScreen: LawyerBottomBarItem(
        label = "Find Projects",
        icon = Icons.Default.Search,
        route = LawyerSearchScreen
    )
    data object MessageListScreen : LawyerBottomBarItem(
        label = "Messages",
        icon = Icons.Default.Message,
        route = ChatListScreen
    )
    data object ProfileScreen: LawyerBottomBarItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route = com.example.legalease.ui.navigation.ProfileScreen
    )
}

