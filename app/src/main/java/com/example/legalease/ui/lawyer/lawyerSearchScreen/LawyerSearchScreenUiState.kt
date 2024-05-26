package com.example.legalease.ui.lawyer.lawyerSearchScreen

data class LawyerSearchScreenUiState(
    val searchQuery: String = "",
    val active: Boolean = false,
    val location:String = "",
    val priceFilter:String = "",
)
