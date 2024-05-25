package com.example.legalease.ui.message.audioToText

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import java.io.File

class AudioRecorderHelper(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null

    fun startRecording() {
        val fileName = "recorded_audio.3gp"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        audioFile = File.createTempFile(fileName, ".3gp", storageDir)

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFile?.absolutePath)
            prepare()
            start()
        }
    }

    fun stopRecording(): File? {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        return audioFile
    }

    fun getRecordedFile(): File? = audioFile
}
