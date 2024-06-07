package com.mentalhealth.eifie.ui.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.BlackGreen

@Composable
fun CommonButton(
    text: String = stringResource(id = R.string.start),
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BlackGreen
        ),
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 60.dp, vertical = 12.dp),
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}