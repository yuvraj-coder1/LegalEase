package com.example.legalease.ui.client.sendCaseToLawyer

import androidx.lifecycle.ViewModel
import com.example.legalease.model.CaseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SendCaseToLawyerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SendCaseToLawyerUiState())
    val uiState: StateFlow<SendCaseToLawyerUiState> = _uiState.asStateFlow()
}