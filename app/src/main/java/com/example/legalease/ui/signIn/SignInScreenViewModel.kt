package com.example.legalease.ui.signIn

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInScreenUiState())
    val uiState: StateFlow<SignInScreenUiState> = _uiState.asStateFlow()
    var inProcess by mutableStateOf(false)
    var isSignedIn by mutableStateOf(false)
    var currentUser = auth.currentUser
    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun updateIsLawyer(isLawyer: Boolean) {
        _uiState.update {
            it.copy(isLawyer = isLawyer)
        }
    }

    fun signIn() {
        inProcess = true
        auth.signInWithEmailAndPassword(_uiState.value.username, _uiState.value.password)
            .addOnSuccessListener {
                isSignedIn = true
                inProcess = false
                currentUser = auth.currentUser
                Log.d("TAG", "signIn: signed in successfully")
            }
            .addOnFailureListener {
                isSignedIn = false
                inProcess = false
                Log.d("TAG", "signIn: failed to sign in")
            }
    }
}