package com.mentalhealth.eifie.ui.chat.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.domain.entities.models.Chat

@Composable
fun ChatListView(
    chats: List<Chat>,
    onItemClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        items(chats.size) { index ->
            ChatItemView(
                chat = chats[index],
                onClick = onItemClick
            )
            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}

@Preview
@Composable
fun ChatListPreview() {
    ChatListView(
        chats = listOf(Chat(
            0,
            "Hola",
            "Gracias",
            "Ayer"),
            Chat(
                0,
                "Hola",
                "Gracias",
                "Ayer"),
            Chat(
                0,
                "Hola",
                "Gracias",
                "Ayer")
        )
    )
}