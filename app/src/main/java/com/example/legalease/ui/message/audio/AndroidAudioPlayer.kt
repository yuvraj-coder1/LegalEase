package com.example.legalease.ui.message.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(uri: Uri) {
        MediaPlayer.create(context, uri).apply {
            player = this
            start()
        }
    }

    override fun getCurrentProgress(): Int {
        return player?.currentPosition ?: 0
    }

    override fun getDuration(): Int {
        return player?.duration ?: 0
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}