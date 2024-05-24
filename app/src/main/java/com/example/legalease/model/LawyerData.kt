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
)

fun LawyerData.toJsonObject(): JSONObject {
    return JSONObject().apply {
        put("id", id)
        put("name", name)
        put("email", email)
        put("password", password)
        put("officialId", officialId)
        put("rating", rating)
        put("ratingCount", ratingCount)
        put("imageUrl", imageUrl)
        put("expertise", expertise)
        put("feesPerHour", feesPerHour)
        put("lawyerType", lawyerType)
        put("bio", bio)
    }
}

