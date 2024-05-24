package com.example.legalease.ui.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.legalease.ui.viewModels.AuthViewModel

@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier
) {

//    val isUserFreeLancer = authViewModel.currentfreelancer == null
    val isUserFreeLancer = false
    val screens = listOf(
        BottomBarItem.HomeScreen,
        BottomBarItem.ProjectScreen,
        BottomBarItem.MessageListScreen,
        BottomBarItem.ProfileScreen,
    )

    val clientScreens = listOf(
        ClientBottomBarItem.HomeScreen,
        ClientBottomBarItem.ProjectScreen,
        ClientBottomBarItem.MessageListScreen,
        ClientBottomBarItem.ProfileScreen,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if(!isUserFreeLancer) {
            screens.forEach { screen ->
                NavigationBarItem(
//                label = {
//                    Text(text = screen.title!!)
//                },
                    icon = {
                        Icon(imageVector = screen.icon, contentDescription = "")
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.Gray,
                        selectedTextColor = Color.Black,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    ),
                )
            }
        } else {
            clientScreens.forEach { screen ->
                NavigationBarItem(
//                label = {
//                    Text(text = screen.title!!)
//                },
                    icon = {
                        Icon(imageVector = screen.icon!!, contentDescription = "")
                    },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = Color.Gray,
                        selectedTextColor = Color.Black,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        indicatorColor = Color.White
                    ),
                )
            }
        }

    }
}
