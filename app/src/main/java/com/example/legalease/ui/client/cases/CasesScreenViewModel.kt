package com.example.legalease.ui.client.cases

import androidx.lifecycle.ViewModel
import com.example.legalease.model.CaseData
import com.example.legalease.model.CaseItemData

class CasesScreenViewModel : ViewModel() {
    val casesItemList: List<CaseData> = listOf(
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Active",
        ),
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Pending",
        ),
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Inactive",
        ),
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Inactive",
        ),
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Pending",
        ),
        CaseData(
            caseType = "Divorce",
            createdAt = "23/01/2005",
            status = "Closed",
        )
    )
}