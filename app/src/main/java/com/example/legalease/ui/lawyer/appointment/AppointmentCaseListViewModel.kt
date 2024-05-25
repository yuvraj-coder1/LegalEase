package com.example.legalease.ui.lawyer.appointment

import androidx.lifecycle.ViewModel
import com.example.legalease.ui.client.sendCaseToLawyer.SendCaseToLawyerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppointmentCaseListViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow<AppointmentCaseListUiState>(AppointmentCaseListUiState())
    val uiState: StateFlow<AppointmentCaseListUiState> = _uiState.asStateFlow()
}