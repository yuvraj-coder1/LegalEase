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
data object AddCaseScreen

@Serializable
data object ClientProfileScreen

@Serializable
data object CaseListScreen

@Serializable
data object ReceivedCaseListScreen

@Serializable
data class CaseDetailScreen(val caseId: String)

@Serializable
data class ComposePdfViewerScreen(val pdfLink: String)

@Serializable
data class SendCaseScreen(val lawyerId: String)

@Serializable
data object SpeechToTextScreen

@Serializable
data object FilterScreen

@Serializable
data object LanguageSelectionScreen

@Serializable
data object BlockedScreen

@Serializable
data object DocumentListScreen

@Serializable
data object AppointmentCaseSelectionScreen

@Serializable
data class BookAppointmentScreen(val caseId: String,val clientId:String)

@Serializable
data class SingleCaseDetailScreen(val caseId: String)

@Serializable
data object LawyerSearchScreen

@Serializable
data object LawyerFilterScreen