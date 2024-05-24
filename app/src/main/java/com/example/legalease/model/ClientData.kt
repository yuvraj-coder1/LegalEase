package com.example.legalease.model

data class ClientData(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val lawyerId: String? = null
)
