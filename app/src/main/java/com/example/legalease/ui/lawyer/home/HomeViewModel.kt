package com.example.legalease.ui.lawyer.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.legalease.model.CaseItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import kotlin.reflect.KProperty

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val casesItemList: MutableList<CaseItemData> = mutableListOf(
        CaseItemData(
            title = "Domestic Violence",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        )
    )
    init {
        val date = LocalDate.now()
        val dayOfWeek = date.dayOfWeek.value
        val dateList = mutableListOf<DatesTODisplay>()
        for (i in 1..7) {
            if (i == dayOfWeek)
                dateList.add(DatesTODisplay(date, selected = true))
            else if (i < dayOfWeek)
                dateList.add(DatesTODisplay(date.minusDays((dayOfWeek - i).toLong())))
            else
                dateList.add(DatesTODisplay(date.plusDays((i - dayOfWeek).toLong())))
        }
        _uiState.update {
            it.copy(dateList = dateList)
        }
    }

    fun selectDate(index: Int) {
        val dateList = mutableListOf<DatesTODisplay>()
        var newDate: LocalDate = LocalDate.now()
        for (i in uiState.value.dateList.indices) {
            dateList.add(DatesTODisplay(uiState.value.dateList[i].date, selected = i == index))
            if (i == index)
                newDate = uiState.value.dateList[i].date
        }
        _uiState.update {
            it.copy(
                dateList = dateList,
                selectedDate = newDate
            )
        }
    }

    fun moveDatesRight() {
        val dateList = mutableListOf<DatesTODisplay>()
        for (i in uiState.value.dateList.indices) {
            dateList.add(DatesTODisplay(uiState.value.dateList[i].date.plusDays(7)))
        }
        _uiState.update {
            it.copy(dateList = dateList)
        }
    }

    fun moveDatesLeft() {
        val dateList = mutableListOf<DatesTODisplay>()
        for (i in uiState.value.dateList.indices) {
            dateList.add(DatesTODisplay(uiState.value.dateList[i].date.minusDays(7)))
        }
        _uiState.update {
            it.copy(dateList = dateList)
        }
    }
}