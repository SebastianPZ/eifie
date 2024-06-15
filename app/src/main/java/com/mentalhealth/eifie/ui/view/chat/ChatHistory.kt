package com.mentalhealth.eifie.ui.view.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.models.ChatUI
import com.mentalhealth.eifie.ui.theme.CustomGray
import java.time.LocalDateTime

@Composable
fun ChatHistory(
    chats: List<ChatUI>,
    onItemClick: (Long) -> Unit = {},
    emptyText: String = "Comience un chat con la IA\npara guardar el historial.",
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        if(chats.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            ) {
                Text(
                    text = emptyText,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = CustomGray
                )
            }
        }
        else {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {
                items(chats.size) { index ->
                    ChatItem(
                        chat = chats[index],
                        onClick = onItemClick
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatHistoryPreview() {
    ChatHistory(
        chats = listOf(
            ChatUI(
                id = 0,
                topic = "Perros",
                lastMessage = "Gracias",
                createdDate = LocalDateTime.now(),
                calendarDate = "Hoy"
            ),
            ChatUI(
                id = 0,
                topic = "Perros",
                lastMessage = "Gracias",
                createdDate = LocalDateTime.now(),
                calendarDate = "Ayer"
            ),
            ChatUI(
                id = 0,
                topic = "Perros",
                lastMessage = "Gracias",
                createdDate = LocalDateTime.now(),
                calendarDate = "2 días"
            )
        )
    )
}