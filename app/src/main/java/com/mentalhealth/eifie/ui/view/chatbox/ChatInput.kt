package com.mentalhealth.eifie.ui.view.chatbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun ChatInput(
    modifier: Modifier = Modifier,
    send:(String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(24.dp)
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp

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
            modifier = Modifier.sizeIn(maxWidth = (screenWidth * 0.68).dp)
        )

        FloatingActionButton(
            containerColor = DarkGreen,
            shape = RoundedCornerShape(100.dp),
            onClick = {
                send(text.text)
                text = TextFieldValue("")
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                tint = CustomWhite,
                contentDescription = "",
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}

@Preview
@Composable
fun ChatInputPreview() {
    ChatInput(send = {})
}