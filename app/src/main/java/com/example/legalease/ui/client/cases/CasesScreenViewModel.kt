package com.example.legalease.ui.client.cases

import androidx.lifecycle.ViewModel
import com.example.legalease.model.CaseItemData

class CasesScreenViewModel : ViewModel() {
    val casesItemList: MutableList<CaseItemData> = mutableListOf(
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
        ),
        CaseItemData(
            title = "case title",
            description = "this is the description of the case giving details of the case to know more about the case and the legal process. the end will be ellipsed if the description is too long",
            upcomingHearing = "22-10-2024"
        )
    )
}