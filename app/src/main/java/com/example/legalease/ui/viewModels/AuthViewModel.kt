package com.example.legalease.ui.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.legalease.data.CHATS
import com.example.legalease.data.CLIENT_NODE
import com.example.legalease.data.LAWYER_NODE
import com.example.legalease.data.MESSAGE
import com.example.legalease.model.ChatData
import com.example.legalease.model.ChatUser
import com.example.legalease.model.ClientData
import com.example.legalease.model.LawyerData
import com.example.legalease.model.Message
import com.example.legalease.ui.message.audio.AndroidAudioPlayer
import com.example.legalease.ui.message.audio.AndroidAudioRecorder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var inProgress by mutableStateOf(false)
    var inProgressChats by mutableStateOf(false)
    var chats by mutableStateOf(emptyList<ChatData>())
    var currentLawyer by mutableStateOf<LawyerData?>(null)
    var currentClient: ClientData? = null
    var chatMessages by mutableStateOf(emptyList<Message>())
    var inProgressChatMessage by mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null
    var isRecording by mutableStateOf(false)
    var isPlaying by mutableStateOf(false)
    var currentProgress by mutableFloatStateOf(0.0f)
    var currentProgressString by mutableStateOf("")
    var currPlayingAudio by mutableStateOf("")

    private val recorder by lazy {
        AndroidAudioRecorder(context)
    }
    private val player by lazy {
        AndroidAudioPlayer(context)
    }
    private var audioFile: File? = null
    private var audioUrl: Uri? = null
    fun startRecording() {
        isRecording = true
        audioFile = File(context.cacheDir, "testRecording.mp3").also {
            recorder.start(it)
        }
    }

    fun playOrPauseAudio(audioUri: String) {
        if (isPlaying) {
            isPlaying = false
            player.stop()
        } else {
            isPlaying = true
            player.playFile(audioUri.toUri())
        }
    }

    private fun uploadFile(chatId: String) {
        val randomName = System.currentTimeMillis().toString()
        storage.reference.child("audio/${randomName}.mp3").putFile(audioFile!!.toUri())
            .addOnSuccessListener {
                it.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    audioUrl = uri
                    onSendMessages(
                        chatId = chatId,
                        message = "",
                        audioUrl = uri.toString(),
                        duration = formatDuration(getAudioDuration(context, uri))
                    )
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "uploadFile: $it")
            }
    }


    @SuppressLint("DefaultLocale")
    fun formatDuration(duration: Int): String {
        val minute = TimeUnit.MILLISECONDS.toMinutes(duration.toLong())
        val seconds = (duration / 1000) % 60
        return String.format("%02d:%02d", minute, seconds)
    }

    fun calculateProgressValue() {
        currentProgress =
            ((player.getCurrentProgress().toFloat() / player.getDuration().toFloat()))
        Log.d(TAG, "calculateProgressValue:$currentProgress ")
        currentProgressString = formatDuration(player.getCurrentProgress())
    }

    private fun getAudioDuration(context: Context, audioUri: Uri): Int {
        val mediaPlayer = MediaPlayer.create(context, audioUri)
        val duration = mediaPlayer.duration
        mediaPlayer.release()
        return duration
    }

    fun stopRecording(chatId: String) {
        isRecording = false
        recorder.stop()
        viewModelScope.launch {
            uploadFile(chatId)
        }
    }

    fun getClient(id: String): ClientData {
        inProgress = true
        var client = ClientData()
        db.collection(CLIENT_NODE).whereEqualTo("id", id).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    client = it.documents[0].toObject(ClientData::class.java)!!
                    currentClient = client
                    inProgress = false
                    inProgressChats = false
                    populateChats()
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "getClient: $it")
                inProgress = false
                inProgressChats = false
            }
        return client
    }

    fun getLawyer(id: String): LawyerData {
        inProgress = true
        var lawyer = LawyerData()
        db.collection(LAWYER_NODE).whereEqualTo("id", id).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    lawyer = it.documents[0].toObject(LawyerData::class.java)!!
                    currentLawyer = lawyer
                    inProgress = false
                    inProgressChats = false
                    populateChats()
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "getClient: $it")
                inProgress = false
                inProgressChats = false
            }
        return lawyer
    }

    fun getCurrentUserId() =
        if (currentClient != null) currentClient?.id ?: "" else currentLawyer?.id ?: ""


    fun populateMessages(chatId: String) {
        inProgressChatMessage = true
        currentChatMessageListener = db.collection(CHATS).document(chatId).collection(MESSAGE)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("TAG", "populateMessages: $error")
                    return@addSnapshotListener
                }
                if (value != null) {
                    chatMessages = value.documents.mapNotNull {
                        it.toObject(Message::class.java)
                    }.sortedBy { it.timeStamp }
                    inProgressChatMessage = false
                }
            }
    }

    fun depopulateMessages() {
        currentChatMessageListener?.remove()
        currentChatMessageListener = null
        chatMessages = emptyList()
    }

    fun populateChats() {
        inProgressChats = true
        if (currentLawyer != null) {
            db.collection(CHATS).whereEqualTo("lawyer.userId", currentLawyer?.id)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.d("TAG", "populateChats: $error")
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        chats = value.documents.mapNotNull {
                            it.toObject(ChatData::class.java)
                        }
                        inProgressChats = false
                    }
                }
        }
        if (currentClient != null) {
            db.collection(CHATS).whereEqualTo("client.userId", currentClient?.id)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.d("TAG", "populateChats: $error")
                        return@addSnapshotListener
                    }
                    if (value != null) {
                        chats = value.documents.mapNotNull {
                            it.toObject(ChatData::class.java)
                        }
                        inProgressChats = false
                    }
                }
        }
    }

    fun addClientToChat(clientId: String) {
        db.collection(CLIENT_NODE).whereEqualTo("id", clientId).get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val client = it.documents[0].toObject(ClientData::class.java)!!
                    val id = db.collection(CHATS).document().id
                    val chat = ChatData(
                        chatId = id,
                        client = ChatUser(
                            userId = client.id,
                            name = client.name,
                            imageUrl = null
                        ),
                        lawyer = ChatUser(
                            userId = currentLawyer?.id ?: "",
                            name = currentLawyer?.name ?: "",
                            imageUrl = null
                        )

                    )
                    db.collection(CHATS).document(id).set(chat)
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "addClientToChat: could not add chat $it ")
            }

    }

    fun onSendMessages(
        chatId: String,
        message: String = "",
        audioUrl: String = "",
        duration: String = ""
    ) {
        val time = Calendar.getInstance().time.toString()
        val msg = Message(
            sendBy =
            if (currentLawyer != null) currentLawyer?.id ?: "" else currentClient?.id ?: "",
            message = message,
            timeStamp = time,
            audioUri = audioUrl,
            audioDuration = duration
        )
        db.collection(CHATS).document(chatId).collection(MESSAGE).document().set(msg)

    }

}