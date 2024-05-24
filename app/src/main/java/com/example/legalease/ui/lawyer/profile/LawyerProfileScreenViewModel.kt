package com.example.legalease.ui.lawyer.profile

import androidx.lifecycle.ViewModel
import com.example.legalease.R
import com.example.legalease.model.LawyerUserRating

class LawyerProfileScreenViewModel : ViewModel() {
    val reviews: List<LawyerUserRating> = listOf(
        LawyerUserRating(
            userName = "liam jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "This is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
        LawyerUserRating(
            userName = "liam jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "This is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
        LawyerUserRating(
            userName = "liam jiang",
            rating = 4.0,
            date = "Dec 2022",
            comment = "This is a good lawyer",
            userPhoto = R.drawable.profile_image,
            likes = 10,
            dislikes = 5
        ),
    )
    fun updateLikes(likes:Int,index:Int) {

    }
    fun updateDislikes(dislikes:Int,index:Int) {

    }

}