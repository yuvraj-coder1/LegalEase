package com.example.legalease.model

data class CaseData(
    val id: String = "",
    val caseType: String = "",
    val description: String = "",
    val documentLinks: List<String> = emptyList(),
    val createdAt: String = "",
    val status: String = "pending",
    val clientId: String = "",
    val lawyerId: String? = null,
    val preferredLanguage: String = "",
)
