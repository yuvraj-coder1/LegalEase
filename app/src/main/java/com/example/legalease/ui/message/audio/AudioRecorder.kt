package com.example.legalease.ui.message.audio

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}