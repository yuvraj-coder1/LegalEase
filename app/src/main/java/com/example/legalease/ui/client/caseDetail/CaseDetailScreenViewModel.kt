package com.example.legalease.ui.client.caseDetail

import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.model.CaseData
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CaseDetailScreenViewModel @Inject constructor(private val db: FirebaseFirestore) :
    ViewModel() {

    val _caseDetail = MutableStateFlow(CaseData())
    val caseDetail = _caseDetail.asStateFlow()

    fun getCaseDetail(caseId: String) {
        db.collection(CASE_NODE).document(caseId).get().addOnSuccessListener {
            _caseDetail.value = it.toObject(CaseData::class.java)!!
        }
    }
}