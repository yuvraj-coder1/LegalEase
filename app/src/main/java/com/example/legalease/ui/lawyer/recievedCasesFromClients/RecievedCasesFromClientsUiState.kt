package com.example.legalease.ui.lawyer.recievedCasesFromClients

import com.example.legalease.model.CaseData
import com.example.legalease.model.CaseDetailSentToLawyer

data class ReceivedCasesFromClientsUiState(
    val cases: List<CaseData> = listOf(
        CaseData(caseType = "case1", description = "description1"),
        CaseData(caseType = "case1", description = "description1"),
        CaseData(caseType = "case1", description = "description1"),
    ),
)
