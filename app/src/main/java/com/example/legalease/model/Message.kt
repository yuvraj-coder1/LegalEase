package com.example.legalease.model

data class Message(
    var sendBy: String = "",
    var message: String = "",
    var audioUri: String = "",
    var audioDuration: String = "",
    var timeStamp: String = ""
)
