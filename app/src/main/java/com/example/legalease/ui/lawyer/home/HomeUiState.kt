package com.example.legalease.ui.lawyer.home

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.legalease.model.AppointmentData
import com.example.legalease.model.CaseData
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class HomeUiState (
    val name: String = "",
    val dateList: List<DatesTODisplay> = emptyList(),
    val selectedDate:LocalDate = LocalDate.now(),
    val appointments: List<AppointmentData> = listOf(),
    val cases: List<CaseData> = listOf()
)

data class DatesTODisplay(
    val date: LocalDate,
    val appointed:Boolean = false,
    var selected:Boolean = false
)
