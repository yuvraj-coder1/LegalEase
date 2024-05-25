package com.example.legalease.ui.lawyer.recievedCasesFromClients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReceivedCasesFromClientsViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ReceivedCasesFromClientsUiState())
    val uiState: StateFlow<ReceivedCasesFromClientsUiState> = _uiState.asStateFlow()
    var case by mutableStateOf<CaseData?>(null)

    fun getCasesFromClients() {
        var cases: List<CaseData>
        db.collection(CASE_NODE).whereEqualTo("lawyerId", auth.currentUser?.uid)
            .whereEqualTo("status", "pending").get()
            .addOnSuccessListener {
                cases = it.toObjects(CaseData::class.java)
                _uiState.value = ReceivedCasesFromClientsUiState(cases)
            }
    }

    fun getCurrentLawyerId(): String {
        return auth.currentUser?.uid.toString()
    }

    fun getCaseById(caseId: String) {
        db.collection(CASE_NODE).document(caseId).get().addOnSuccessListener {
            case = it.toObject(CaseData::class.java)
        }
    }

    //update case status depending on lawyer choice
    fun updateCaseStatus(caseId: String, status: String, lawyerId: String) {
        if (status == "rejected"){
            db.collection(CASE_NODE).document(caseId).update("status", "pending")
            db.collection(CASE_NODE).document(caseId).update("lawyerId", null)
        }
        else {
            db.collection(CASE_NODE).document(caseId).update("status", "active")
            db.collection(CASE_NODE).document(caseId).update("lawyerId", lawyerId)
        }

    }
}