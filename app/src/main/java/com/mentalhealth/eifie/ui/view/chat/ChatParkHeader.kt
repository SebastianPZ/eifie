package com.mentalhealth.eifie.ui.view.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun ChatParkHeader(
    title: String,
    options: @Composable () -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row {
            options()
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    ChatParkHeader(
        title = "Chats",
        options = {
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
    )
}