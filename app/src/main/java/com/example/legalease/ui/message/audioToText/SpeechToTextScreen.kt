package com.example.legalease.ui.message.audioToText

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.io.File

//
//@Composable
//fun SpeechToTextScreen() {
//    val context = LocalContext.current
//    val speechToTextHelper = remember { SpeechToTextHelper(context) }
//    var text by remember { mutableStateOf("Press the button and start speaking") }
//    var isListening by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = text, textAlign = TextAlign.Center)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                if (isListening) {
//                    speechToTextHelper.stopListening()
//                } else {
//                    speechToTextHelper.startListening(
//                        onResult = {
//                            text = it
//                            isListening = false
//                        },
//                        onError = {
//                            text = it
//                            isListening = false
//                        }
//                    )
//                }
//                isListening = !isListening
//            }
//        ) {
//            Text(if (isListening) "Stop Listening" else "Start Listening")
//        }
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            speechToTextHelper.destroy()
//        }
//    }
//
//    RequestMicrophonePermission { granted ->
//        if (!granted) {
//            text = "Microphone permission is required to use this feature"
//        }
//    }
//}

@Composable
fun SpeechToTextScreen() {
    val context = LocalContext.current
    val speechToTextHelper = remember { SpeechToTextHelper(context) }
    val audioRecorderHelper = remember { AudioRecorderHelper(context) }
    var text by remember { mutableStateOf("Press the button and start speaking") }
    var isListening by remember { mutableStateOf(false) }
    var recordedFile by remember { mutableStateOf<File?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isListening) {
                    speechToTextHelper.stopListening()
                    recordedFile = audioRecorderHelper.stopRecording()
                    recordedFile?.let {
                        // Handle the recorded file (e.g., upload, save, etc.)
                    }
                } else {
                    audioRecorderHelper.startRecording()
                    speechToTextHelper.startListening(
                        onResult = {
                            text = it
                            isListening = false
                            recordedFile = audioRecorderHelper.stopRecording()
                        },
                        onError = {
                            text = it
                            isListening = false
                            recordedFile = audioRecorderHelper.stopRecording()
                        }
                    )
                }
                isListening = !isListening
            }
        ) {
            Text(if (isListening) "Stop Listening" else "Start Listening")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            speechToTextHelper.destroy()
        }
    }

    RequestPermissions { granted ->
        if (!granted) {
            text = "Permissions are required to use this feature"
        }
    }
}

