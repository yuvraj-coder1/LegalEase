package com.example.legalease.ui.lawyer.appointment

import com.example.legalease.model.CaseData

data class AppointmentCaseListUiState(
    val caseList:List<CaseData> = listOf(),
)
