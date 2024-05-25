package com.example.legalease.ui.signIn

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CLIENT_NODE
import com.example.legalease.data.LAWYER_NODE
import com.example.legalease.model.ClientData
import com.example.legalease.model.LawyerData
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

    fun signIn(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        inProcess = true
        auth.signInWithEmailAndPassword(_uiState.value.username, _uiState.value.password)
            .addOnSuccessListener {
                currentUser = auth.currentUser
                if (_uiState.value.isLawyer) {
                    db.collection(LAWYER_NODE).document(currentUser!!.uid).get()
                        .addOnSuccessListener {
                            Log.d("TAG", "signIn: $it")
                            val lawyerData = it.toObject(LawyerData::class.java)
                            Log.d("TAG", "signIn: $lawyerData")
                            if (lawyerData == null) {
                                inProcess = false
                                onFailure(Exception("Lawyer not found"))
                                return@addOnSuccessListener
                            }
                            if (lawyerData.isVerified == "true") {
                                isSignedIn = true
                                inProcess = false
                                onSuccess()
                            } else {
                                inProcess = false
                                onFailure(Exception("Lawyer is not verified"))
                            }
                        }
                        .addOnFailureListener {
                            inProcess = false
                            onFailure(Exception("Lawyer not found"))
                        }
                } else {
                    db.collection(CLIENT_NODE).document(currentUser!!.uid).get()
                        .addOnSuccessListener {
                            val clientData = it.toObject(ClientData::class.java)
                            if (clientData == null) {
                                inProcess = false
                                onFailure(Exception("Client not found"))
                                return@addOnSuccessListener
                            }
                            isSignedIn = true
                            inProcess = false
                            onSuccess()
                        }
                        .addOnFailureListener {
                            inProcess = false
                            onFailure(Exception("Client not found"))
                        }
                }
                Log.d("TAG", "signIn: signed in successfully")
            }
            .addOnFailureListener {
                isSignedIn = false
                inProcess = false
                onFailure(it)
                Log.d("TAG", "signIn: failed to sign in")
            }
    }
}