package com.mentalhealth.eifie.ui.view.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.Supporter
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.theme.BlackGreen

@Composable
fun ChatBotItem(
    supBot: Supporter?,
    onNewMessage: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 15.dp, bottom = 10.dp,
                    start = 10.dp, end = 15.dp
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserPhotoView(
                    uri = supBot?.photo,
                    username = supBot?.name,
                    modifier = Modifier.size(60.dp),
                    fontSize = 14.sp
                )
                Text(
                    text = supBot?.name ?: "",
                    fontSize = 16.sp,
                    color = BlackGreen,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(15.dp),
                color = BlackGreen,
                modifier = Modifier.clickable {
                    onNewMessage()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_new_message),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatBotItemPreview() {
    ChatBotItem(supBot = Supporter(
        id = 0,
        name = "Carlos",
        photo = null,
        config = ""),
        onNewMessage = {}
    )
}

