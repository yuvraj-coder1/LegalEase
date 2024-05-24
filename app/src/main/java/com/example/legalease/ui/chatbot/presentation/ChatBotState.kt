package com.example.legalease.ui.chatbot.presentation

import android.graphics.Bitmap
import com.example.legalease.ui.chatbot.data.Chat

data class ChatBotState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)