package com.mentalhealth.eifie.ui.chat.chatbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun ChatBoxInputText(
    modifier: Modifier = Modifier,
    send:(String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it },
            shape = RoundedCornerShape((25.0).dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = LightSkyGray,
                focusedContainerColor = LightSkyGray,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
        )

        IconButton(
            colors = IconButtonColors(
                containerColor = DarkGreen,
                contentColor = CustomWhite,
                disabledContainerColor = DarkGreen,
                disabledContentColor = CustomWhite
            ),
            onClick = { send(text.text) }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                tint = CustomWhite,
                contentDescription = ""
            )
        }
    }
}