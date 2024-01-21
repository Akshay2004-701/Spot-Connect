package com.akshay.spotconnect.chat

import android.widget.Toast
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.akshay.spotconnect.location.LocationViewModel
import com.akshay.spotconnect.ui.theme.SpotConnectTheme
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatViewModel  = viewModel(),
    locationVM:LocationViewModel = viewModel(factory = LocationViewModel.factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val address by locationVM.address.collectAsState()
    locationVM.getAddress(locationVM.lat.value, locationVM.lon.value, LocalContext.current)

    Scaffold(
        bottomBar = {
            MessageInput(
                onSendMessage = viewModel::sendMessage,
                resetScroll = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                },
                address = address
            )
        },
        containerColor = Color.Black,
        topBar = {
            ChatTopAppBar ()
        }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()

        ){
            ChatList(messageList = uiState.messages, listState =listState)
        }
    }

}

@Composable
fun ChatList(
    messageList:List<ChatMessage>,
    listState:LazyListState
) {
    LazyColumn(
        state = listState,
        reverseLayout = true
    ){
        items(messageList.reversed()){message->
            ChatItem(chatMessage = message)
        }
    }
}

@Composable
fun ChatItem(
    chatMessage: ChatMessage
) {
    val isModel = chatMessage.participant == Participant.MODEL ||
            chatMessage.participant == Participant.ERROR

    val itemShape = if (isModel){
        RoundedCornerShape(4.dp,20.dp,20.dp,20.dp)
    }else{
        RoundedCornerShape(20.dp,4.dp,20.dp,20.dp)
    }

    val bgColor = when(chatMessage.participant){
        Participant.USER->{
            Color(89, 14, 150)
        }
        Participant.MODEL->{
            Color(33, 86, 219)
        }
        Participant.ERROR->{
            MaterialTheme.colorScheme.errorContainer
        }
    }

    val horizontalAlignment = if (isModel){
        Alignment.Start
    }else{
        Alignment.End
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = chatMessage.participant.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row {
            if (chatMessage.isPending){
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            BoxWithConstraints {
                Card(
                    colors = CardDefaults.cardColors(containerColor = bgColor),
                    shape = itemShape,
                    modifier = Modifier.widthIn(0.dp,maxWidth*0.9f)
                ) {
                    Text(
                        text = chatMessage.text,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    resetScroll: () -> Unit = {},
    address:String
) {
    var userMessage by rememberSaveable { mutableStateOf("") }
    val ctx = LocalContext.current
    val kb = LocalSoftwareKeyboardController.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(21, 5, 43)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = userMessage,
                label = { Text(text = "prompt") },
                onValueChange = { userMessage = it },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.85f)
            )
            IconButton(
                onClick = {
                    if (userMessage.isNotBlank()) {
                        if (userMessage.contains("near me")){
                            var s = userMessage.removeRange(userMessage.length-2,userMessage.length)
                            s+=address
                            onSendMessage(s)
                        }
                        else { onSendMessage(userMessage) }
                        userMessage = ""
                        resetScroll()
                        kb?.hide()
                    }
                    else{
                        Toast.makeText(
                            ctx,
                            "Invalid Input",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.15f),
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    Icons.Filled.Send,
                    contentDescription = "send",
                    modifier = Modifier
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopAppBar() {
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "SEARCH ASSISTANT",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(33, 86, 219),))
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Items() {
    SpotConnectTheme {
//        ChatScreen()
    }
}
