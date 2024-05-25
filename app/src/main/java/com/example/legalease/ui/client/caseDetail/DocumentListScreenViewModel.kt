package com.example.legalease.ui.client.caseDetail

import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.example.legalease.ui.lawyer.recievedCasesFromClients.ReceivedCasesFromClientsUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DocumentListScreenViewModel@Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _caseListForDocument = MutableStateFlow<List<CaseData>>(emptyList())
    val caseListForDocument  = _caseListForDocument.asStateFlow()
    init {
        getCasesFromClients()
    }
    fun getCasesFromClients() {
        var cases: List<CaseData>
        db.collection(CASE_NODE).whereEqualTo("clientId", auth.currentUser?.uid)
            .whereEqualTo("status", "pending").get()
            .addOnSuccessListener {
                cases = it.toObjects(CaseData::class.java)
                _caseListForDocument.value = cases
            }
    }
}