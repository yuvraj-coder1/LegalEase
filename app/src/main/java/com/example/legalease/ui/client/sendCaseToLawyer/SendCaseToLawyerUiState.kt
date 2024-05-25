package com.example.legalease.ui.client.sendCaseToLawyer

import com.example.legalease.model.CaseData

data class SendCaseToLawyerUiState(
    val caseList:List<CaseData> = listOf(CaseData(),CaseData(),CaseData(),CaseData(),CaseData(),CaseData(),CaseData()),
)
