package com.mentalhealth.eifie.ui.view.chatbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun ChatHeader(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint = BlackGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp).clickable { onBack() }
        )
        Icon(
            imageVector = Icons.Filled.Menu,
            tint = DarkGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview
@Composable
fun ChatHeaderPreview() {
    ChatHeader(onBack = { /*TODO*/ })
}