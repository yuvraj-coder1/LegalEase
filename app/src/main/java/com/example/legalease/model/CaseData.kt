package com.example.legalease.model

data class CaseData(
    val id: String = "",
    val caseType: String = "Divorce",
    val description: String = "Lorem ipsum dolor sit amet, consectetuer " +
            "adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. " +
            "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur\n",
    val documentLinks: List<String> = emptyList(),
    val createdAt: String = "23/01/24",
    val status: String = "pending",
    val clientId: String = "",
    val lawyerId: String? = null,
    val preferredLanguage: String = "",
)
