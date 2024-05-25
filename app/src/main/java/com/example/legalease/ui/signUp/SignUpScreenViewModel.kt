package com.example.legalease.ui.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.legalease.data.CLIENT_NODE
import com.example.legalease.data.LAWYER_NODE
import com.example.legalease.model.ClientData
import com.example.legalease.model.LawyerData
import com.example.legalease.ui.signIn.SignInScreenUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.exp

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpScreenUiState())
    val uiState: StateFlow<SignUpScreenUiState> = _uiState.asStateFlow()
    fun updateUsername(username: String) {
        _uiState.update {
            it.copy(username = username)
        }
    }

    fun updateLocation(location: String){
        _uiState.update {
            it.copy(lawyerLocation = location)
        }
    }

    fun updateExperience(experience: String) {
        _uiState.update {
            it.copy(lawyerExperience = experience)
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

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updateGovernmentId(govermentId: String) {
        _uiState.update {
            it.copy(governmentId = govermentId)
        }
    }

    fun updateLawyerType(lawyerType: String) {
        _uiState.update {
            it.copy(lawyerType = lawyerType)
        }
    }

    fun updateAboutLawyer(aboutLawyer: String) {
        _uiState.update {
            it.copy(aboutLawyer = aboutLawyer)
        }
    }

    fun updateFee(fee: String) {
        _uiState.update {
            it.copy(fee = fee)
        }
    }

    fun selectExpertise(index: Int) {
        if (uiState.value.selectedExpertise.contains(uiState.value.expertise[index])) {
            val selectedExpertise = uiState.value.selectedExpertise.toMutableList()
            selectedExpertise.remove(uiState.value.expertise[index])
            val isThisExpertiseSelected = uiState.value.isThisExpertiseSelected.toMutableList()
            isThisExpertiseSelected[index] = false
            _uiState.update {
                it.copy(
                    selectedExpertise = selectedExpertise,
                    isThisExpertiseSelected = isThisExpertiseSelected
                )
            }
            Log.d("Selected Expertise", uiState.value.selectedExpertise.toString())
            Log.d("Selected Expertise", uiState.value.isThisExpertiseSelected.toString())
        } else {
            val selectedExpertise = uiState.value.selectedExpertise.toMutableList()
            selectedExpertise.add(uiState.value.expertise[index])
            val isThisExpertiseSelected = uiState.value.isThisExpertiseSelected.toMutableList()
            isThisExpertiseSelected[index] = true
            _uiState.update {
                it.copy(
                    selectedExpertise = selectedExpertise,
                    isThisExpertiseSelected = isThisExpertiseSelected
                )
            }
            Log.d("Selected Expertise", uiState.value.selectedExpertise.toString())
            Log.d("Selected Expertise", uiState.value.isThisExpertiseSelected.toString())
        }
    }

    fun signUp() {
        if (uiState.value.password != uiState.value.confirmPassword) {
            _uiState.update {
                it.copy(error = "Passwords do not match")
            }
            return
        }
        auth.createUserWithEmailAndPassword(uiState.value.email, uiState.value.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { user ->
                        if (uiState.value.isLawyer) {
                            val lawyer = LawyerData(
                                id = user.uid,
                                name = uiState.value.username,
                                officialId = uiState.value.governmentId,
                                email = uiState.value.email,
                                password = uiState.value.password,
                                expertise = uiState.value.selectedExpertise,
                                bio = uiState.value.aboutLawyer,
                                feesPerHour = uiState.value.fee,
                                lawyerType = uiState.value.lawyerType,
                                lawyerLocation = uiState.value.lawyerLocation,
                                experience = uiState.value.lawyerExperience
                            )
                            db.collection(LAWYER_NODE).document(user.uid).set(lawyer)
                        } else {
                            val client = ClientData(
                                id = user.uid,
                                name = uiState.value.username,
                                email = uiState.value.email,
                                password = uiState.value.password
                            )
                            db.collection(CLIENT_NODE).document(user.uid).set(client)
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(error = task.exception?.message)
                    }
                }

            }
            .addOnFailureListener {
                _uiState.update { state ->
                    state.copy(error = it.message)
                }
            }
    }


}