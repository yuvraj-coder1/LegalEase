package com.example.legalease.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.legalease.ui.chatbot.presentation.ChatBotScreen
import com.example.legalease.ui.client.addCaseScreen.AddCaseScreen
import com.example.legalease.ui.client.caseDetail.CasesDetailScreen
import com.example.legalease.ui.client.caseDetail.DocumentListScreen
import com.example.legalease.ui.client.cases.CasesScreen
import com.example.legalease.ui.client.profile.ClientProfile
import com.example.legalease.ui.client.searchScreen.FilterScreen
import com.example.legalease.ui.client.searchScreen.SearchScreen
import com.example.legalease.ui.client.searchScreen.SearchScreenViewModel
import com.example.legalease.ui.client.searchScreen.SearchedLawyerDetailScreen
import com.example.legalease.ui.client.sendCaseToLawyer.SendCaseToLawyerScreen
import com.example.legalease.ui.languageSelection.LanguageSelectionScreen
import com.example.legalease.ui.lawyer.appointment.AppointmentCaseListScreen
import com.example.legalease.ui.lawyer.appointment.BookAppointment
import com.example.legalease.ui.lawyer.blocked.BlockedLawyerScreen
import com.example.legalease.ui.lawyer.home.LawyerHomeScreen
import com.example.legalease.ui.lawyer.lawyerGetStartedScreen.LawyerGetStartedScreen
import com.example.legalease.ui.lawyer.lawyerSearchScreen.LawyerFilterScreen
import com.example.legalease.ui.lawyer.lawyerSearchScreen.LawyerSearchScreen
import com.example.legalease.ui.lawyer.lawyerSearchScreen.LawyerSearchScreenViewModel
import com.example.legalease.ui.lawyer.profile.LawyerProfileScreen
import com.example.legalease.ui.lawyer.recievedCasesFromClients.CaseDetailOfTheReceivedCase
import com.example.legalease.ui.lawyer.recievedCasesFromClients.ReceivedCasesFromClients
import com.example.legalease.ui.message.ChatListScreen
import com.example.legalease.ui.message.SingleChatScreen
import com.example.legalease.ui.message.audioToText.SpeechToTextScreen
import com.example.legalease.ui.pdfViewer.ComposePDFViewer
import com.example.legalease.ui.signIn.SignInScreen
import com.example.legalease.ui.signIn.SignInScreenViewModel
import com.example.legalease.ui.signUp.SignUpScreen
import com.example.legalease.ui.signUp.SignUpScreenViewModel
import com.example.legalease.ui.viewModels.AuthViewModel
import com.example.legalease.ui.welcome.WelcomeScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LegalEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    signInScreenViewModel: SignInScreenViewModel,
    signUpScreenViewModel: SignUpScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel,
    lawyerSearchScreenViewModel: LawyerSearchScreenViewModel,
    authViewModel: AuthViewModel,
    onBottomBarVisibilityChanged: (Boolean) -> Unit,
    showChatbotIcon: (Boolean) -> Unit,
    showIncomingCases: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        startDestination = LanguageSelectionScreen,
        modifier = modifier
    ) {
        composable<SignInScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            SignInScreen(
                onSignUpClicked = {
                    navController.navigate(SignUpScreen)
                },
                signInViewModel = signInScreenViewModel,
                navigateToHome = {
                    if (signInScreenViewModel.uiState.value.isLawyer) {
                        authViewModel.getLawyer(signInScreenViewModel.currentUser?.uid ?: "")
                    } else {
                        authViewModel.getClient(signInScreenViewModel.currentUser?.uid ?: "")
                    }
                    navController.navigate(HomeScreen)
                },
                navigateToBlockedScreen = {
                    navController.navigate(BlockedScreen)
                }
            )
        }
        composable<SignUpScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            SignUpScreen(
                onSignInClicked = {
                    navController.navigate(SignInScreen)
                },
                signUpViewModel = signUpScreenViewModel,
                navController = navController
            )
        }

        composable<ChatBotScreen> {
            showChatbotIcon(false)
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            ChatBotScreen()
        }
        composable<ChatListScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(true)
            ChatListScreen(
                vm = authViewModel,
                onChatClicked = { id ->
                    navController.navigate(SingleChatScreen(id = id))
                }
            )

        }
        composable<SingleChatScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            val args = it.toRoute<SingleChatScreen>()
            SingleChatScreen(
                vm = authViewModel,
                isLawyer = signInScreenViewModel.uiState.value.isLawyer,
                chatId = args.id,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<SearchScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(true)
            SearchScreen(
                onLawyerClicked = {
                    navController.navigate(
                        SearchedLayerScreen(
                            it.id
                        )
                    )
                },
                onFilterClicked = {
                    navController.navigate(FilterScreen)
                },
                searchScreenViewModel = searchScreenViewModel
            )
        }
        composable<ProfileScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(true)
            LawyerProfileScreen(logOut = {
                signInScreenViewModel.isSignedIn = false
                authViewModel.signOut()
                navController.navigate(LanguageSelectionScreen) {
                    popUpTo(LanguageSelectionScreen) {
                        inclusive = false
                    }
                }
            })
        }
        composable<HomeScreen> {
            onBottomBarVisibilityChanged(true)
            showChatbotIcon(true)
            if (signInScreenViewModel.uiState.value.isLawyer) {
                showIncomingCases(true)
            }
            LawyerHomeScreen(
                signedInViewModel = signInScreenViewModel,
                onSeeAllClick = { navController.navigate(CaseListScreen) },
                navigateToAddAppointment = { navController.navigate(AppointmentCaseSelectionScreen) },
                onDocumentClick = { navController.navigate(DocumentListScreen) },
                navigateToCaseDetails = { navController.navigate(SingleCaseDetailScreen(it)) }
            )
        }
        composable<SearchedLayerScreen> {
            showIncomingCases(false)
            val args = it.toRoute<SearchedLayerScreen>()
            onBottomBarVisibilityChanged(false)
            SearchedLawyerDetailScreen(
                lawyerId = args.lawyerId,
                navigateToAddCaseScreen = { lawyerId ->
                    navController.navigate(SendCaseScreen(lawyerId))
                },
                vm = authViewModel,
            )
        }
        composable<LawyerGetStartedScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            LawyerGetStartedScreen(
                viewModel = signUpScreenViewModel,
                navigateToSignIn = {
                    navController.navigate(SignInScreen) {
                        popUpTo(SignInScreen) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        composable<WelcomeScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            showChatbotIcon(false)
            WelcomeScreen { navController.navigate(SignInScreen) }
        }
        composable<AddCaseScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            AddCaseScreen(navigateToHome = { navController.navigate(HomeScreen) })
        }
        composable<ClientProfileScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(true)
            ClientProfile(viewModel = authViewModel, logOut = {
                signInScreenViewModel.isSignedIn = false
                authViewModel.signOut()
                navController.navigate(LanguageSelectionScreen) {
                    popUpTo(LanguageSelectionScreen) {
                        inclusive = false
                    }
                }
            })
        }
        composable<CaseListScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(true)
            CasesScreen()
        }
        composable<ReceivedCaseListScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            ReceivedCasesFromClients(
                navigateToDetailScreen = {
                    navController.navigate(
                        CaseDetailScreen(it)
                    )
                }
            )
        }
        composable<CaseDetailScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            val args = it.toRoute<CaseDetailScreen>()
            CaseDetailOfTheReceivedCase(
                caseId = args.caseId,
                navigateToHome = { navController.navigate(HomeScreen) },
                addToChatList = { clientId, lawyerId ->
                    scope.launch {
                        authViewModel.addClientToChat(
                            clientId,
                            lawyerId
                        )
                    }
                },
                onDocumentClick = { navController.navigate(ComposePdfViewerScreen(it)) }
            )
        }

        composable<ComposePdfViewerScreen> {
            showIncomingCases(false)
            val args = it.toRoute<ComposePdfViewerScreen>()
            onBottomBarVisibilityChanged(false)
            ComposePDFViewer(pdfLink = args.pdfLink)
        }
        composable<SendCaseScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            val args = it.toRoute<SendCaseScreen>()
            SendCaseToLawyerScreen(
                lawyerId = args.lawyerId,
                navigateUp = { navController.navigateUp() })
        }
        composable<SpeechToTextScreen> {
            SpeechToTextScreen()
        }
        composable<FilterScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            FilterScreen(viewModel = searchScreenViewModel)
        }
        composable<BlockedScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            BlockedLawyerScreen()
        }
        composable<LanguageSelectionScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            LanguageSelectionScreen(
                navController = navController,
                onClicked = { navController.navigate(WelcomeScreen) }
            )
        }
        composable<DocumentListScreen> {
            showIncomingCases(false)
            onBottomBarVisibilityChanged(false)
            DocumentListScreen(onClick = { navController.navigate(ComposePdfViewerScreen(it)) })
        }

        composable<AppointmentCaseSelectionScreen> {
            onBottomBarVisibilityChanged(false)
            showIncomingCases(false)
            AppointmentCaseListScreen(navigateToBookAppointment = { caseId, clientId ->
                navController.navigate(BookAppointmentScreen(caseId, clientId))
            },
                navigateToCaseDetails = {
                    navController.navigate(SingleCaseDetailScreen(it))
                }
            )
        }
        composable<BookAppointmentScreen> {
            onBottomBarVisibilityChanged(false)
            showIncomingCases(false)
            val args = it.toRoute<BookAppointmentScreen>()
            BookAppointment(
                caseId = args.caseId,
                clientId = args.clientId,

                navigateBack = { navController.navigateUp() })
        }

        composable<SingleCaseDetailScreen> {
            onBottomBarVisibilityChanged(false)
            showIncomingCases(false)
            val args = it.toRoute<SingleCaseDetailScreen>()
            CasesDetailScreen(
                caseId = args.caseId,
                onDocumentClick = { navController.navigate(ComposePdfViewerScreen(it)) },

                )
        }
        composable<LawyerSearchScreen> {
            onBottomBarVisibilityChanged(true)
            showIncomingCases(false)
            LawyerSearchScreen(onFilterClicked = {navController.navigate(LawyerFilterScreen)})
        }
        composable<LawyerFilterScreen> {
            onBottomBarVisibilityChanged(false)
            showIncomingCases(false)
            LawyerFilterScreen(viewModel = lawyerSearchScreenViewModel, onNavigate = {navController.navigateUp()})
        }
    }
}

