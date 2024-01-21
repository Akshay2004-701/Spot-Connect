package com.akshay.spotconnect.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel :ViewModel() {
    private val config = generationConfig { temperature = 0.7f }

    private val model = GenerativeModel(
        modelName = MODEL_NAME,
        apiKey = API_KEY,
        generationConfig = config
    )


    private val chat = model.startChat(history = listOf())

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(
        ChatUiState(
            messages = listOf(
                ChatMessage(
                    text = "Hi, I am your search assistant, how may I help you?",
                    participant = Participant.MODEL,
                    isPending = false
                )
            )
        )
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(userInput:String){
        _uiState.value.addMessage(
            ChatMessage(
                text = userInput,
                participant = Participant.USER,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = chat.sendMessage(userInput)
                _uiState.value.replaceLastPendingMessage()

                response.text?.let {msg->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = msg,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            }catch (e:Exception){
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.message?:"Unknown error has occurred",
                        participant = Participant.ERROR,
                        isPending = false
                    )
                )
            }
        }
    }

    companion object{
        const val API_KEY = "AIzaSyBC2NOarS10QxveyVYgAkhG4ESL5UfCaTA"
        const val MODEL_NAME = "gemini-pro"
    }
}