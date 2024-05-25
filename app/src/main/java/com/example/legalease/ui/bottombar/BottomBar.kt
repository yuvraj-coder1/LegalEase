package com.example.legalease.ui.bottombar

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.legalease.ui.signIn.SignInScreenViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier,
    signedInViewModel: SignInScreenViewModel
) {

    val uiState = signedInViewModel.uiState.collectAsState()
    val isUserLawyer = uiState.value.isLawyer

    val screens = listOf(
        BottomBarItem.HomeScreen,
        BottomBarItem.ProjectScreen,
        BottomBarItem.MessageListScreen,
        BottomBarItem.ProfileScreen,
    )

    val clientScreens = listOf(
        LawyerBottomBarItem.HomeScreen,
        LawyerBottomBarItem.ProjectScreen,
        LawyerBottomBarItem.MessageListScreen,
        LawyerBottomBarItem.ProfileScreen,
    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if (!isUserLawyer) {
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

