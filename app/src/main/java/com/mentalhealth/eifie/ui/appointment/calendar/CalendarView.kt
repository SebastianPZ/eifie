package com.mentalhealth.eifie.ui.appointment.calendar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.appointment.list.AppointmentViewModel
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.Pink
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.SkyBlue
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.manager.DateInfo
import java.util.Date

@Composable
fun CalendarView(
    viewModel: AppointmentViewModel,
    modifier: Modifier = Modifier
) {
    val calendarState by viewModel.calendarState.collectAsStateWithLifecycle()
    var icon by remember { mutableIntStateOf(R.drawable.ic_expand) }

    var calendarAlpha = if (calendarState is CalendarViewState.Expanded) 1f else 0f


    Card(
        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column(
            modifier = Modifier
                .background(brush = Brush.linearGradient(
                    colors = listOf(
                        SkyBlue,
                        Pink
                    )
                ))
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            ) {
                IconButton(
                    onClick = {  },
                    enabled = calendarState is CalendarViewState.Expanded
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).alpha(calendarAlpha)
                    )
                }
                Text(
                    text = "Marzo",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {  },
                    enabled = calendarState is CalendarViewState.Expanded
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).alpha(calendarAlpha)
                    )
                }
            }
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, end = 5.dp, bottom = 15.dp)
            ) {
                items(viewModel.daysHeader.size) {
                    Text(text = viewModel.daysHeader[it], fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                }
            }
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp)
            ) {
                when(calendarState) {
                    is CalendarViewState.Condense -> (calendarState as CalendarViewState.Condense).run {
                        items(days.size) {
                            CalendarDateItem(days[it], viewModel.updateAppointmentsByDate)
                        }
                    }
                    is CalendarViewState.Expanded -> (calendarState as CalendarViewState.Expanded).run {
                        items(days.size) {
                            CalendarDateItem(days[it], viewModel.updateAppointmentsByDate)
                        }
                    }
                    else -> Unit
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 10.dp)
            ) {
                Text(text = "")
                IconButton(
                    onClick = {
                        viewModel.updateCalendarState()
                        icon = if(viewModel.calendarState.value is CalendarViewState.Expanded) R.drawable.ic_condense
                        else R.drawable.ic_expand
                    }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarDateItem(
    dateInfo: DateInfo,
    onClick: (date: Date) -> Unit
) {
    TextButton(
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .drawBehind {
                drawCircle(
                    color = if (dateInfo.isToday) Color.White else Color.Transparent,
                    radius = 50f
                )
            },
        onClick = { onClick(dateInfo.date) }) {
        Text(
            text = if(dateInfo.inCurrentMonth) dateInfo.day else emptyString(),
            fontSize = 14.sp,
            color = if(dateInfo.isToday) Purple else BlackGreen,
            textAlign = TextAlign.Center
        )
    }
}