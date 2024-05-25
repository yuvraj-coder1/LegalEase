package com.example.legalease.ui.message.audioToText

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestMicrophonePermission(
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            onPermissionResult(isGranted)
        }

//    LaunchedEffect(Unit) {
//        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
//    }
}
