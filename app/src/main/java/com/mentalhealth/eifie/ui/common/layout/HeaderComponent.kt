package com.mentalhealth.eifie.ui.common.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.cutiveFontFamily

@Composable
fun HeaderComponent(
    title: String,
    onBack: () -> Unit,
    subtitle: String = stringResource(id = R.string.configuration),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth().wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 5.dp)
        ) {
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontFamily = cutiveFontFamily
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                tint = BlackGreen,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    HeaderComponent(
        title = "Cita",
        subtitle = "Agendamiento",
        onBack = {}
    )
}