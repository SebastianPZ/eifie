package com.mentalhealth.eifie.ui.register.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.cutiveFontFamily

@Composable
fun RegisterHeaderComponent(
    step: Int = 0,
    stepsSize: Int = 0,
    title: String = ""
) {
    Text(
        modifier = Modifier.padding(start = 24.dp, top = 45.dp),
        text = stringResource(id = R.string.step, step, stepsSize),
        fontSize = 12.sp,
        fontFamily = cutiveFontFamily
    )
    Text(
        modifier = Modifier.padding(start = 24.dp, top = 2.dp),
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}