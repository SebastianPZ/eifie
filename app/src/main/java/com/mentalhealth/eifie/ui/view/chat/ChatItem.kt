package com.mentalhealth.eifie.ui.view.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.models.ChatUI
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import java.time.LocalDateTime

@Composable
fun ChatItem(
    chat: ChatUI,
    onClick: (Long) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = Modifier.clickable {
            onClick(chat.id)
        }
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp,
                    start = 8.dp, end = 15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserPhotoView(
                    modifier = Modifier.size(50.dp),
                    fontSize = 14.sp
                )
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = chat.topic, fontSize = 14.sp, color = BlackGreen)
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(text = chat.lastMessage, fontSize = 12.sp, color = LightGray)
                }
            }
            Text(
                text = chat.calendarDate,
                color = LightGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ChatItemPreview() {
    ChatItem(chat = ChatUI(1, "Perros", "Gracias", createdDate = LocalDateTime.now(), calendarDate = "Hoy"))
}