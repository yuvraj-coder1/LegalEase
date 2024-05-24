package com.example.legalease.ui.message.audio

import android.net.Uri
import java.io.File

interface AudioPlayer {
    fun playFile(uri: Uri)
    fun stop()
    fun getCurrentProgress(): Int
    fun getDuration(): Int
}