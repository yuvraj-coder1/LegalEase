package com.example.legalease.model

data class ChatData(
    val chatId: String = "",
    val client: ChatUser = ChatUser(),
    val lawyer: ChatUser = ChatUser(),
)

data class ChatUser(
    val userId: String = "",
    val name: String = "",
    val imageUrl: String? = null
)