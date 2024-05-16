package com.mentalhealth.eifie.ui.chat.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.chat.list.ChatListView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun ChatView(
    navController: NavHostController
) {

    val viewModel = hiltViewModel<ChatViewModel>()

    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Column {
            ChatHeader(
                title = stringResource(id = R.string.chats),
                options = {
                    Row {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = DarkGreen,
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                tint = DarkGreen,
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                },
                modifier = Modifier.padding(vertical = 32.dp)
            )
            when(state) {
                is ChatViewState.Success -> (state as ChatViewState.Success).run {
                    ChatListView(
                        chats = chats,
                        onItemClick = { chatId -> navController.navigate("${Router.CHAT_BOX.route}$chatId") }
                    )
                }
                else -> Unit
            }
        }
    }

}