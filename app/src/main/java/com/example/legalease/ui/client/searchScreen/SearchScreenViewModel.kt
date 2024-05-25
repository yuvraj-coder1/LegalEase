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

    init {
        _uiState.value = _uiState.value.copy(isIndexSelected = List(tagList.size) { false })
    }

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

    fun updateLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
        Log.d("TAG", "updateLocation: ${uiState.value.location}")
    }

    fun updateExperience(experience: Float) {
        _uiState.value = _uiState.value.copy(experienceFilter = experience)
        Log.d("TAG", "updateExperience: ${uiState.value.experienceFilter}")
    }

    fun updateIsIndexSelected(index: Int) {
        val copylist = _uiState.value.expertiseFilter.toMutableList()
        if (uiState.value.isIndexSelected[index]) {
            copylist.remove(tagList[index])
            _uiState.value = _uiState.value.copy(
                isIndexSelected = _uiState.value.isIndexSelected.toMutableList().apply {
                    this[index] = false
                }, expertiseFilter = copylist
            )
        } else {
            copylist.add(tagList[index])
            _uiState.value = _uiState.value.copy(
                isIndexSelected = _uiState.value.isIndexSelected.toMutableList().apply {
                    this[index] = true
                },
                expertiseFilter = copylist
            )
        }
        Log.d("TAG", "updateIsIndexSelected: ${uiState.value.isIndexSelected}")
        Log.d("TAG", "updateExpertise: ${uiState.value.expertiseFilter}")
    }

    fun updateIsRatingSelected(index: Int) {
        if (uiState.value.isRatingSelected[index])
            _uiState.value = _uiState.value.copy(
                isRatingSelected = _uiState.value.isRatingSelected.toMutableList().apply {
                    this[index] = false
                })
        else {
            val newList = List(ratingList.size) { false }
            _uiState.value = _uiState.value.copy(
                isRatingSelected = newList
            )
            _uiState.value = _uiState.value.copy(
                isRatingSelected = _uiState.value.isRatingSelected.toMutableList().apply {
                    this[index] = true
                }, ratingFilter = ratingList[index]
            )
            Log.d("TAG", "updateIsRatingSelected: ${uiState.value.ratingFilter}")
        }
    }


}