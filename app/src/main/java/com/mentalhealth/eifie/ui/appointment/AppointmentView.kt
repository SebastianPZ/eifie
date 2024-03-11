package com.mentalhealth.eifie.ui.appointment

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dropdown.DropdownItem
import com.mentalhealth.eifie.ui.common.dropdown.EDropdownField
import com.mentalhealth.eifie.ui.common.textfield.DropdownFieldValues
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.ui.theme.Pink
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.ui.theme.SkyBlue
import com.mentalhealth.eifie.ui.theme.White60
import com.mentalhealth.eifie.ui.theme.White85
import com.mentalhealth.eifie.util.emptyString
import com.mentalhealth.eifie.util.manager.DateInfo
import java.util.Date

@Composable
fun Appointment(
    viewModel: AppointmentViewModel = hiltViewModel<AppointmentViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ConstraintLayout {
        val (calendarCard, column) = createRefs()

        CalendarCard(
            viewModel = viewModel,
            modifier =  Modifier.constrainAs(calendarCard) {
                top.linkTo(parent.top)
            }
        )
        when(state) {
            is AppointmentViewState.Success, is AppointmentViewState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(column) {
                            top.linkTo(calendarCard.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    when(state) {
                        is AppointmentViewState.Success -> (state as AppointmentViewState.Success).run {
                            if(appointments.isEmpty) {
                                Text(
                                    text = "No cuenta con citas programadas.",
                                    fontSize = 12.sp,
                                    color = DarkGray
                                )
                            }
                            else {
                                Text(text = "Si hay informacion")
                            }
                        }
                        else -> (state as AppointmentViewState.Error).run {
                            Text(
                                text = message,
                                fontSize = 12.sp,
                                color = DarkGray
                            )
                        }
                    }
                }
            }
            is AppointmentViewState.Loading -> {
                EAnimation(
                    resource = R.raw.loading_animation,
                    animationModifier = Modifier
                        .size(150.dp),
                    backgroundModifier = Modifier
                        .fillMaxSize()
                        .background(color = White60)
                )
            }
        }
    }
}

@Composable
fun CalendarCard(
    viewModel: AppointmentViewModel,
    modifier: Modifier
) {
    val calendarState by viewModel.calendarState.collectAsStateWithLifecycle()
    var icon by remember { mutableIntStateOf(R.drawable.ic_expand) }

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
            EDropdownField(
                values = DropdownFieldValues(
                    initialValue = 0,
                    items = listOf(DropdownItem(0, "Marzo"), DropdownItem(1, "Septiembre")),
                    color = Color.Black,
                    borderColor = Color.Transparent,
                    modifier = Modifier
                        .width(180.dp)
                        .padding(start = 5.dp),
                    dropdownModifier = Modifier
                        .padding(start = 10.dp)
                )
            )
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, bottom = 15.dp)
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
            .drawBehind { drawCircle(
                color = if (dateInfo.isToday) Color.White else Color.Transparent,
                radius = 50f
            )
        },
        onClick = { onClick(dateInfo.date) }) {
        Text(
            text = if(dateInfo.inCurrentMonth) dateInfo.day else emptyString(),
            fontSize = 14.sp,
            color = if(dateInfo.isToday) Purple else DarkGray,
            textAlign = TextAlign.Center
        )
    }
}