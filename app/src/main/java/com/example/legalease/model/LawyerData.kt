package com.example.legalease.model

import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class LawyerData(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val officialId: String = "",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val imageUrl: String = "",
    val expertise: List<String> = listOf("skill 1", "skill 2"),
    val feesPerHour: String = "",
    val lawyerType: String = "",
    val bio: String = "",
    val experience: String = "",
    val lawyerLocation: String = "Bangalore",
    val verified: Boolean = true
)


