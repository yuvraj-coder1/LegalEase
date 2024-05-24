package com.example.legalease.ui.client.addCaseScreen

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CASE_NODE
import com.example.legalease.data.CHATS
import com.example.legalease.model.CaseData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddCaseViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ViewModel() {
    private val _addCaseUiState = MutableStateFlow(AddCaseUiState())
    val addCaseUiState: StateFlow<AddCaseUiState> = _addCaseUiState.asStateFlow()

    var pdfUriList: List<Uri> = emptyList()
    var pdfUrlList = emptyList<String>()
    fun uploadPdfToDataBase() {
        val storageRef = Firebase.storage.reference
        val pdfRef = storageRef.child("documents/case_pdf.pdf")
        pdfUriList.forEach {
            pdfRef.putFile(it)
                .addOnSuccessListener {
                    pdfRef.downloadUrl.addOnSuccessListener { uri ->
                        pdfUrlList += uri.toString()
                    }
                    Log.d("PDF", "PDF uploaded")
                }
                .addOnFailureListener {
                    Log.d("PDF", "$it PDF not uploaded")
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendCase(lawyerId: String) {
        val id = db.collection(CASE_NODE).document().id
        val case = CaseData(
            id = id,
            caseType = _addCaseUiState.value.caseType,
            description = _addCaseUiState.value.description,
            preferredLanguage = _addCaseUiState.value.languagePreference,
            documentLinks = pdfUrlList,
            clientId = auth.currentUser?.uid ?: "test",
            createdAt = LocalDate.now().toString(),
            lawyerId = lawyerId
        )
        db.collection(CASE_NODE).document(id).set(case)
    }

    fun updateTitle(title: String) {
        _addCaseUiState.value = _addCaseUiState.value.copy(caseType = title)
    }

    fun updateDescription(description: String) {
        _addCaseUiState.value = _addCaseUiState.value.copy(description = description)
    }

    fun updateLanguagePreference(languagePreference: String) {
        _addCaseUiState.value = _addCaseUiState.value.copy(languagePreference = languagePreference)
    }


}