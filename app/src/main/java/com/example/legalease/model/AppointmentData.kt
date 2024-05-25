package com.example.legalease.model

data class AppointmentData(
    val id: String="",
    val reason: String="",
    val lawyerId:String="",
    val date: String="",
    val time: String="",
    val clientId: String="",
    val caseId: String="",
)
