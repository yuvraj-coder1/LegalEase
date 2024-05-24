package com.example.legalease.ui.lawyer.recievedCasesFromClients

import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReceivedCasesFromClientsViewModel @Inject constructor(private val db: FirebaseFirestore) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ReceivedCasesFromClientsUiState())
    val uiState: StateFlow<ReceivedCasesFromClientsUiState> = _uiState.asStateFlow()

    private fun getCasesFromClients(lawyerId: String) {
        var cases: List<CaseData>
        db.collection(CASE_NODE).whereEqualTo("lawyerId", lawyerId)
            .whereEqualTo("status", "pending").get()
            .addOnSuccessListener {
                cases = it.toObjects(CaseData::class.java)
                _uiState.value = ReceivedCasesFromClientsUiState(cases)
            }
    }

    //update case status depending on lawyer choice
    fun updateCaseStatus(caseId: String, status: String, lawyerId: String) {
        if (status == "rejected")
            db.collection(CASE_NODE).document(caseId).update("status", status)
        else {
            db.collection(CASE_NODE).document(caseId).update("status", status)
            db.collection(CASE_NODE).document(caseId).update("lawyerId", lawyerId)
        }

    }
}