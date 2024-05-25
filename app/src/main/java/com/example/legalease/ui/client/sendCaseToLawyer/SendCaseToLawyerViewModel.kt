package com.example.legalease.ui.client.sendCaseToLawyer

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
class SendCaseToLawyerViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    auth: FirebaseAuth
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(SendCaseToLawyerUiState())
    val uiState: StateFlow<SendCaseToLawyerUiState> = _uiState.asStateFlow()

    init {
        getCases(auth.currentUser!!.uid)
    }

    fun getCases(clientId: String) {
        db.collection(CASE_NODE).whereEqualTo("clientId", clientId)
            .whereEqualTo("lawyerId", null)
            .get()
            .addOnSuccessListener {
                val cases = it.toObjects(CaseData::class.java)
                _uiState.value = SendCaseToLawyerUiState(caseList = cases)
            }

    }

    fun updateCase(caseId: String, lawyerId: String) {
        db.collection(CASE_NODE).document(caseId).update("lawyerId", lawyerId)
        db.collection(CASE_NODE).document(caseId).update("status", "pending")
    }
}