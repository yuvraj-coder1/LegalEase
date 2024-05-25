package com.example.legalease.ui.lawyer.appointment

import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.example.legalease.ui.client.sendCaseToLawyer.SendCaseToLawyerUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppointmentCaseListViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(AppointmentCaseListUiState())
    val uiState: StateFlow<AppointmentCaseListUiState> = _uiState.asStateFlow()

    init {
        getCaseList()
    }

    private fun getCaseList() {
        db.collection(CASE_NODE).whereEqualTo("lawyerId", auth.currentUser?.uid).get()
            .addOnSuccessListener {
                val caseList = it.toObjects(CaseData::class.java)
                _uiState.update { uiState ->
                    uiState.copy(
                        caseList = caseList
                    )
                }
            }
    }
}