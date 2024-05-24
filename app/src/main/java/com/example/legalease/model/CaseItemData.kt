package com.example.legalease.model

import com.example.legalease.R

data class CaseItemData(
val title:String,
val description :String,
val upcomingHearing:String,
val profileImage:Int = R.drawable.default_profile_image,
)
