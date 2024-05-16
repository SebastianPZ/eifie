package com.mentalhealth.eifie.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.domain.entities.models.Message
import com.mentalhealth.eifie.ui.theme.MyMessageColor
import com.mentalhealth.eifie.ui.theme.OtherMessageColor

@Composable
fun ChatBubble(
    message: Message
) {
    Row(
        horizontalArrangement = if (message.fromMe) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            color = if (message.fromMe) MyMessageColor else OtherMessageColor,
            shape = RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15f.dp,
                bottomStart = if (message.fromMe) 15.dp else 0.dp,
                bottomEnd = if (message.fromMe) 0.dp else 15.dp
            ),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }
    }
}