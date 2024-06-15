package com.mentalhealth.eifie.ui.view.chatbox

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
import com.mentalhealth.eifie.ui.models.MessageUI
import com.mentalhealth.eifie.ui.theme.MyMessageColor
import com.mentalhealth.eifie.ui.theme.OtherMessageColor

@Composable
fun MessageHistoryBubble(
    message: MessageUI
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            color = if (message.isFromMe) MyMessageColor else OtherMessageColor,
            shape = RoundedCornerShape(
                topStart = 15.dp,
                topEnd = 15f.dp,
                bottomStart = 0.dp,
                bottomEnd = 15.dp
            ),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
            )
        }
    }
}