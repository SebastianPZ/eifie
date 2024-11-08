package com.mentalhealth.eifie.ui.view.appointment.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.manager.DateInfo
import java.util.Date

@Composable
fun CalendarDateItem(
    dateInfo: DateInfo,
    onClick: (date: DateInfo) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        text = if (dateInfo.inCurrentMonth) dateInfo.day else emptyString(),
        fontSize = 14.sp,
        color = if (dateInfo.isSelected) DarkGreen else BlackGreen,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(6.dp)
            .drawBehind {
                drawCircle(
                    color = if (dateInfo.isSelected) Color.White else Color.Transparent,
                    radius = 50f
                )
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick(dateInfo)
            }
    )
}