package com.example.legalease.ui.message.audioToText

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestPermissions(
    onPermissionResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val requestPermissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = permissions.values.all { it }
            onPermissionResult(allGranted)
        }

//    LaunchedEffect(Unit) {
//        requestPermissionsLauncher.launch(
//            arrayOf(
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//        )
//    }
}
