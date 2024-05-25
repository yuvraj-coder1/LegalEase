package com.example.legalease.ui.lawyer.appointment

import androidx.lifecycle.ViewModel
import com.example.legalease.data.APPOINTMENT
import com.example.legalease.model.AppointmentData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    fun addAppointment(
        reason: String,
        date: String,
        time: String,
        caseId: String,
        clientId: String
    ) {
        val id = db.collection(APPOINTMENT).document().id
        val appointment = AppointmentData(
            id = id,
            reason = reason,
            date = date,
            time = time,
            clientId = clientId,
            caseId = caseId,
            lawyerId = auth.currentUser?.uid?:""
        )
        db.collection(APPOINTMENT).document(id).set(appointment)
    }
}