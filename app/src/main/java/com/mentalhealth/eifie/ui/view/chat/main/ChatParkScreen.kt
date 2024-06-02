package com.mentalhealth.eifie.ui.view.chat.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.SupBot
import com.mentalhealth.eifie.ui.view.chat.history.ChatHistory
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.viewmodel.ChatParkViewModel

@Composable
fun ChatParkScreen(
    navController: NavHostController?,
    viewModel: ChatParkViewModel?
) {
    val chatsHistory = viewModel?.chatsHistory?.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ChatParkHeader(
                title = stringResource(id = R.string.chats),
                options = {
                    Row {
                        SearchButton { }
                        MoreOptionsButton { }

                    }
                },
                modifier = Modifier.padding(top = 25.dp, bottom = 20.dp)
            )
            ChatHistory(
                chats = chatsHistory?.value ?: listOf(),
                onItemClick = { chatId -> navController?.navigate("${Router.CHAT_BOX.route}$chatId") },
                modifier = Modifier.weight(1f)
            )
            ChatBotItem(
                supBot = SupBot(id = 0, name = "Inami", config = ""),
                modifier = Modifier.padding(bottom = 25.dp),
                onNewMessage = { viewModel?.saveBot() }
            )
        }

    }
}

@Composable
private fun SearchButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = DarkGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
private fun MoreOptionsButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            tint = DarkGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview
@Composable
fun ChatParkPreview() {
    Surface (
        color = CustomWhite
    ) {
        ChatParkScreen(null, null)
    }
}