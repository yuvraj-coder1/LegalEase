package com.example.legalease.ui.message

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Horizontal
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.model.Message
import com.example.legalease.ui.viewModels.AuthViewModel
import com.example.ui.theme.Inter
import kotlinx.coroutines.delay

@Composable
fun SingleChatScreen(
    modifier: Modifier = Modifier,
    vm: AuthViewModel,
    chatId: String,
    onBack: () -> Unit,
    isLawyer: Boolean = false
) {
    var reply by rememberSaveable { mutableStateOf("") }
    val currentChat = vm.chats.first { it.chatId == chatId }
    val chatUser = if (isLawyer) currentChat.client else currentChat.lawyer
    LaunchedEffect(key1 = Unit) {
        vm.populateMessages(chatId)
    }
    LaunchedEffect(key1 = vm.currentProgress) {
        delay(500)
        vm.calculateProgressValue()
    }
    BackHandler {
        onBack()
        vm.depopulateMessages()
    }

    Column {
        MessageHeader(name = chatUser.name, imageUrl = chatUser.imageUrl ?: "", onBack = {
            onBack()
            vm.depopulateMessages()
        })
        ChatBox(
            modifier = modifier.weight(1f),
            chatMessages = vm.chatMessages,
            chatUserId = chatUser.userId,
            onPlay = {
                vm.currPlayingAudio = it
                vm.playOrPauseAudio(it)
                vm.calculateProgressValue()
            },
            currPlaying = vm.currPlayingAudio,
            progress = vm.currentProgress,
            currentDuration = vm.currentProgressString,
            isLawyer = isLawyer
        )
        MessageBox(
            message = reply,
            onMessageChange = { reply = it },
            onSend = {
                if (reply.isEmpty()) {
                    if (vm.isRecording) {
                        vm.stopRecording(chatId)
                    } else
                        vm.startRecording()
                } else {
                    vm.onSendMessages(chatId, reply)
                    reply = ""

                }
            },
            isRecording = vm.isRecording,
        )
    }

}

@Composable
fun ChatBox(
    modifier: Modifier = Modifier,
    chatMessages: List<Message>,
    chatUserId: String,
    onPlay: (String) -> Unit = {},
    currPlaying: String = "",
    progress: Float = 0f,
    currentDuration: String,
    isLawyer: Boolean
) {
    LazyColumn(modifier = modifier) {
        items(chatMessages) { msg ->
            val alignment =
                if (msg.sendBy != chatUserId) Alignment.End else Alignment.Start
            val color =
                if (msg.sendBy != chatUserId) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
            if (msg.audioUri != "") {
                AudioBar(
                    color = color,
                    onPlay = { onPlay(msg.audioUri) },
                    alignment = alignment,
                    duration = msg.audioDuration,
                    progress = if (currPlaying == msg.audioUri) progress else 0f,
                    currentDuration = if (currPlaying == msg.audioUri) currentDuration else "00:00"
                )


            } else
                MessageCard(message = msg, alignment = alignment, color = color)
        }
    }
}

@Composable
fun AudioBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    color: Color,
    currentDuration: String = "00:00",
    duration: String = "00:00",
    onPlay: () -> Unit = {},
    alignment: Horizontal
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = alignment,
    ) {
        var isPlayingScreen by rememberSaveable { mutableStateOf(false) }
        Row(
            modifier = modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = color)
                .padding(12.dp)
                .padding(start = 8.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier) {
                LinearProgressIndicator(progress = { progress })
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = currentDuration,
                        fontFamily = Inter,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(145.dp))
                    Text(
                        text = duration,
                        color = MaterialTheme.colorScheme.onPrimary, fontFamily = Inter
                    )
                }
            }
            Spacer(modifier = Modifier.width(18.dp))
            IconButton(
                modifier = Modifier.size(30.dp),
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.onPrimary),
                onClick = {
                    onPlay()
                    isPlayingScreen = !isPlayingScreen
                },
            ) {
                if (isPlayingScreen) {
                    Icon(imageVector = Icons.Default.Pause, contentDescription = "Stop Recording")
                } else
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Record Audio")
            }
        }
    }
}

@Composable
fun MessageCard(message: Message, alignment: Horizontal, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = alignment,
    ) {
        Text(
            text = message.message,
            fontFamily = Inter,
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .background(color)
                .padding(15.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
    }
}

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun MessageCardPreview() {
//
//    MessageCard(
//        message = Message(message = "Hello"),
//        alignment = Alignment.CenterHorizontally,
//        color = Color.Blue
//    )
//}

@Composable
fun MessageHeader(name: String, imageUrl: String = "", onBack: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 10.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .padding(8.dp)
                .clickable { onBack() }
        )
        AsyncImage(
            model = imageUrl,
            error = painterResource(id = R.drawable.default_profile_image),
            contentDescription = "Image",
            modifier = Modifier
                .padding(8.dp)
                .size(36.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Inter
        )
    }
}

@Composable
fun MessageBox(
    message: String,
    modifier: Modifier = Modifier,
    onSend: () -> Unit,
    onMessageChange: (String) -> Unit,
    isRecording: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            placeholder = {
                Text(
                    text = stringResource(R.string.message),
                    fontWeight = FontWeight.Normal,
                    fontFamily = Inter
                )
            },
            onValueChange = onMessageChange,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSend() })
        )
        Button(
            modifier = Modifier.clip(CircleShape),
            onClick = onSend
        ) {
            if (message.isEmpty()) {
                if (isRecording)
                    Icon(

                        imageVector = Icons.Default.Pause,
                        contentDescription = "Stop Recording"
                    )
                else
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Record Audio"
                    )
            } else
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
        }
    }
}