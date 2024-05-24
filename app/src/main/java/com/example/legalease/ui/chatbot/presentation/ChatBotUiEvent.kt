package com.example.legalease.ui.chatbot.presentation

import android.graphics.Bitmap

sealed class ChatBotUiEvent {
    data class UpdatePrompt(val newPrompt: String) : ChatBotUiEvent()
    data class SendPrompt(
        val prompt: String,
        val bitmap: Bitmap?
    ) : ChatBotUiEvent()
}

