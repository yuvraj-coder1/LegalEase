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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
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
    suspend fun uploadPdfToDataBase(): List<String> {
        val storageRef = Firebase.storage.reference
        val pdfUrlList = mutableListOf<String>()

        withContext(Dispatchers.IO) {
            pdfUriList.forEach { uri ->
                val pdfRef = storageRef.child("documents/${uri.lastPathSegment}")
                try {
                    pdfRef.putFile(uri).await()
                    val downloadUrl = pdfRef.downloadUrl.await()
                    pdfUrlList += downloadUrl.toString()
                    Log.d("PDF", "PDF uploaded")
                } catch (e: Exception) {
                    Log.d("PDF", "$e PDF not uploaded")
                }
            }
        }
        return pdfUrlList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendCase() {
        val id = db.collection(CASE_NODE).document().id

        // Launch a coroutine scope to handle asynchronous operations
        CoroutineScope(Dispatchers.Main).launch {
            // Await the completion of PDF uploads
            val pdfUrlList = uploadPdfToDataBase()

            val case = CaseData(
                id = id,
                caseType = _addCaseUiState.value.caseType,
                description = _addCaseUiState.value.description,
                preferredLanguage = _addCaseUiState.value.languagePreference,
                documentLinks = pdfUrlList,
                clientId = auth.currentUser?.uid ?: "test",
                createdAt = LocalDate.now().toString(),
                lawyerId = null
            )

            db.collection(CASE_NODE).document(id).set(case)
        }
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