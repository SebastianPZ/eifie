package com.mentalhealth.eifie.ui.chat.chatbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.chat.ChatBubble
import com.mentalhealth.eifie.ui.theme.CustomWhite

@Composable
fun ChatBoxView(
    chatId: Long,
    navController: NavHostController
) {

    val viewModel = hiltViewModel<ChatBoxViewModel, ChatBoxViewModel.ChatBoxViewModelFactory>(
        creationCallback = { factory -> factory.create(chatId = chatId) })

    val messages by viewModel.messages.collectAsState()


    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomWhite)
            .padding(vertical = 25.dp, horizontal = 24.dp)
    ) {
        ChatBoxHeader(
            onBack = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        ) {
            items(messages) {message ->
                ChatBubble(
                    message = message
                )
            }
        }
        ChatBoxInputText(
            send = { viewModel.sendMessage(it) },
            modifier = Modifier.padding(top = 5.dp))
    }
}

@Preview
@Composable
fun ChatBoxPreview() {
    ChatBoxView(
        chatId = 0,
        navController = rememberNavController()
    )
}