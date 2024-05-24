package com.example.legalease.ui.client.searchScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.legalease.data.LAWYER_NODE
import com.example.legalease.model.LawyerData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val db: FirebaseFirestore) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = _uiState.asStateFlow()
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    private val _lawyerList = MutableStateFlow<List<LawyerData>>(emptyList())
    val lawyerList: StateFlow<List<LawyerData>> = _lawyerList.asStateFlow()

    fun getLawyerDetailsFromSearch(query: String) {
        Log.d("TAG", "getLawyerDetailsFromSearch: $query")
        db.collection(LAWYER_NODE)
            .whereEqualTo("name", query)
            .get()
            .addOnSuccessListener { documents ->
                val lawyerDetails = documents.toObjects(LawyerData::class.java)
                _lawyerList.value = lawyerDetails
                Log.d("TAG", "getLawyerDetailsFromSearch:$lawyerDetails ")
            }
    }

    fun updateActive(active: Boolean) {
        _uiState.value = _uiState.value.copy(active = active)
    }

}