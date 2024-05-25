package com.example.legalease.ui.message

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.ui.components.CommonProgressBar
import com.example.legalease.ui.viewModels.AuthViewModel

@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    vm: AuthViewModel,
    onChatClicked: (String) -> Unit = {}
) {
    val progress = vm.inProgressChats
    if (progress) {
        CommonProgressBar()
    } else {
        val chats = vm.chats
        Column(modifier = modifier.fillMaxSize()) {
            if (chats.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(R.string.no_chats_available))
                }
            } else {
                LazyColumn(modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)) {
                    items(chats) { chat ->
                        val chatPartner =
                            if (vm.currentLawyer != null) chat.client else chat.lawyer
                        ChatListItem(imageUrl = chatPartner.imageUrl, name = chatPartner.name) {
                            onChatClicked(chat.chatId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatListItem(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    name: String,
    onClick: () -> Unit,

) {
    Card( modifier = modifier
        .fillMaxWidth()
        .height(75.dp)
        .padding(8.dp)
        .clickable { onClick.invoke() }) {
        Row(
           verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                error = painterResource(id = R.drawable.default_profile_image),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(text = name, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
        }
    }
}
