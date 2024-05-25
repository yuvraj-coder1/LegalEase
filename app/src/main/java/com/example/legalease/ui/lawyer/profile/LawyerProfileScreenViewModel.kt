package com.example.legalease.ui.lawyer.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.legalease.R
import com.example.legalease.data.LAWYER_NODE
import com.example.legalease.model.LawyerData
import com.example.legalease.model.LawyerUserRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LawyerProfileScreenViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _lawyer = MutableStateFlow(LawyerData())
    val lawyer = _lawyer.asStateFlow()
     fun getLawyer() {
        val lawyerId = auth.currentUser?.uid
        if (lawyerId != null) {
            db.collection(LAWYER_NODE)
                .document(lawyerId)
                .get().addOnSuccessListener {
                    _lawyer.value = it.toObject(LawyerData::class.java)!!
                }
        }
    }

    val reviews: List<LawyerUserRating> = listOf(
        LawyerUserRating(
            userName = "Liam Jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "He is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
        LawyerUserRating(
            userName = "Liam Jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "He is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
        LawyerUserRating(
            userName = "Liam Jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "He is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
    )

    fun updateLikes(likes: Int, index: Int) {

    }

    fun updateDislikes(dislikes: Int, index: Int) {

    }

}