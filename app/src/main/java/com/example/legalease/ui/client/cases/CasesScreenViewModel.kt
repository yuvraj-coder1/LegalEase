package com.example.legalease.ui.client.cases

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.example.legalease.model.CaseItemData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CasesScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {
    init {
        getCases()
    }

    val _casesItemList = MutableStateFlow<caseScreenUiState>(caseScreenUiState())
    var casesItemList = _casesItemList.asStateFlow()

    fun getCases() {
        db.collection(CASE_NODE).whereEqualTo("clientId", auth.currentUser?.uid).get()
            .addOnSuccessListener {
                val casesItemList = it.toObjects(CaseData::class.java)
                _casesItemList.update {
                    it.copy(
                        casesItemList = casesItemList
                    )
                }
                Log.d(TAG, "getCases: $casesItemList")
            }
    }
}

data class caseScreenUiState(
    val casesItemList: List<CaseData> = listOf()

)