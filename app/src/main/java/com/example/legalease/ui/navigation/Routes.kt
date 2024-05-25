package com.example.legalease.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignInScreen

@Serializable
object SignUpScreen

@Serializable
object HomeScreen

@Serializable
object ProfileScreen

@Serializable
object EditProfileScreen

@Serializable
object SearchScreen

@Serializable
data object ChatListScreen

@Serializable
data class SingleChatScreen(val id: String)

@Serializable
data object ChatBotScreen

@Serializable
data class SearchedLayerScreen(val lawyerId: String)

@Serializable
data object LawyerGetStartedScreen

@Serializable
data object WelcomeScreen

@Serializable
data class AddCaseScreen(val lawyerId: String)

@Serializable
data object ClientProfileScreen

@Serializable
data object CaseListScreen

@Serializable
data object ReceivedCaseListScreen

@Serializable
data class CaseDetailScreen(val caseId: String)