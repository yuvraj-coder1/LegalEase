package com.example.legalease.ui.signUp

data class SignUpScreenUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val governmentId: String = "",
    val isLawyer: Boolean = true,
    val error: String? = null,
    val lawyerType: String = "",
    val aboutLawyer:String = "",
    val expertise:List<String> = listOf("Real Estate","Property","Business","Family matters"),
    val fee:String = "",
    val selectedExpertise:List<String> = listOf(),
    val isThisExpertiseSelected:List<Boolean> = listOf(false,false,false,false),
    val lawyerLocation:String= "",
)
