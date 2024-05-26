package com.example.legalease.ui.lawyer.lawyerSearchScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.example.legalease.model.ClientData
import com.example.legalease.model.LawyerData
import com.example.legalease.ui.client.searchScreen.SearchScreenUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LawyerSearchScreenViewModel@Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {
    private val _uiState = MutableStateFlow(LawyerSearchScreenUiState())
    val uiState: StateFlow<LawyerSearchScreenUiState> = _uiState.asStateFlow()
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    private val _caseList = MutableStateFlow<List<CaseData>>(emptyList())
    val caseList: StateFlow<List<CaseData>> = _caseList.asStateFlow()

    fun getCasesFromClients() {
        var cases: List<CaseData>
        db.collection(CASE_NODE).whereEqualTo("clientId", auth.currentUser?.uid)
            .whereEqualTo("status", "inactive").get()
            .addOnSuccessListener {
                cases = it.toObjects(CaseData::class.java)
                _caseList.value = cases
            }
    }
    fun updateActive(active: Boolean) {
        _uiState.value = _uiState.value.copy(active = active)
    }

    fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
        Log.d("TAG", "updateLocation: ${uiState.value.location}")
    }
}