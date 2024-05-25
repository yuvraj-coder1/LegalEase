package com.example.legalease

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.LegalEaseTheme
import com.example.legalease.ui.bottombar.BottomBar
import com.example.legalease.ui.navigation.AddCaseScreen
import com.example.legalease.ui.navigation.ChatBotScreen
import com.example.legalease.ui.navigation.HomeScreen
import com.example.legalease.ui.navigation.LegalEaseApp
import com.example.legalease.ui.navigation.ReceivedCaseListScreen
import com.example.legalease.ui.signIn.SignInScreenTopBar
import com.example.legalease.ui.signIn.SignInScreenViewModel
import com.example.legalease.ui.signUp.SignUpScreenViewModel
//import com.example.legalease.ui.theme.LegalEaseTheme
import com.example.legalease.ui.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.RECORD_AUDIO
            ),
            1
        )
        enableEdgeToEdge()
        setContent {
            val viewModel: SignInScreenViewModel by viewModels()
            val navController = rememberNavController()
            var buttonsVisible by remember { mutableStateOf(false) }
            var showChatbot by remember { mutableStateOf(false) }
            var showIncomingCase by remember { mutableStateOf(false) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            Log.d(TAG, "onCreate:${navBackStackEntry?.destination?.route} ")
            LegalEaseTheme {
                Scaffold(
                    floatingActionButton = {
                        if (navBackStackEntry?.destination?.route == "com.example.legalease.ui.navigation.HomeScreen")
                            FloatingActionButton(
                                modifier = Modifier.padding(16.dp),

                                onClick = { navController.navigate(AddCaseScreen) }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.background
                                )
                            }
                    },
                    bottomBar = {
                        // Show bottom bar only if buttonsVisible is true
                        if (buttonsVisible) {
                            BottomBar(
                                navController = navController,
                                state = buttonsVisible,
                                modifier = Modifier,
                                signedInViewModel = viewModel
                            )
                        }
                    },
                    modifier = Modifier,
                    topBar = {
                        SignInScreenTopBar(
                            modifier = Modifier,
                            onClick = { navController.navigate(ChatBotScreen) },
                            showIcon = showChatbot,
                            showIncomingCase = showIncomingCase,
                            onIncomingCaseClick = { navController.navigate(ReceivedCaseListScreen) }
                        )
                    }
                ) { innerPadding ->

                    val signInViewModel: SignInScreenViewModel by viewModels()
                    val signUpViewModel: SignUpScreenViewModel by viewModels()
                    val authViewModel: AuthViewModel by viewModels()
                    LegalEaseApp(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        signInScreenViewModel = signInViewModel,
                        signUpScreenViewModel = signUpViewModel,
                        authViewModel = authViewModel,
                        onBottomBarVisibilityChanged = { buttonsVisible = it },
                        showChatbotIcon = { showChatbot = it },
                        showIncomingCases = { showIncomingCase = it }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LegalEaseTheme {
        Greeting("Android")
    }
}