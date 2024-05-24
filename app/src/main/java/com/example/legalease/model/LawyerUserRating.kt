package com.example.legalease.model

data class LawyerUserRating(
    val userName: String,
    val date: String,
    val rating: Double,
    val comment: String,
    val userPhoto:Int,
    val likes:Int,
    val dislikes:Int
)
