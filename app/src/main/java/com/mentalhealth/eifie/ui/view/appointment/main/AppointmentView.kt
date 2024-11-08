package com.mentalhealth.eifie.ui.view.appointment.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentStyle
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.domain.entities.UserAppointment
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.view.appointment.calendar.CalendarComponent
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.White60

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppointmentView(
    navController: NavHostController?,
    viewModel: AppointmentViewModel = hiltViewModel<AppointmentViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val role by viewModel.userRole.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            when(role) {
                Role.PATIENT -> Unit
                else -> FloatingActionButton(
                    onClick = { navController?.navigate(Router.APPOINTMENT_REGISTER.route) },
                    shape = CircleShape,
                    containerColor = BlackGreen
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_calendar),
                        tint = Color.White,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        }
    ) {
        Column {
            CalendarComponent(
                viewModel = viewModel
            )
            when(state) {
                is ViewState.Success, is ViewState.Error -> {
                    when(state) {
                        is ViewState.Success -> state.run {
                            if(appointments.isEmpty) {
                                EmptyAppointmentListView()
                            }
                            else {
                                AppointmentSummaryView(
                                    appointmentList = appointments
                                )
                            }
                        }
                        else -> (state as ViewState.Error).run {
                            EmptyAppointmentListView(
                                message = message
                            )
                        }
                    }
                }
                else -> {
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
}

@Composable
fun AppointmentSummaryView(
    appointmentList: UserAppointment,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
    ) {
        items(appointmentList.appointments.size) { index ->
            if(appointmentList.appointments[index].list.isNotEmpty()) {
                AppointmentListView(
                    appointments = appointmentList.appointments[index].list,
                    title = appointmentList.appointments[index].style.title,
                    style = appointmentList.appointments[index].style,
                    isLast = index >= appointmentList.appointments.size - 1
                )
            }
        }
    }

}

@Composable
fun AppointmentListView(
    title: String,
    appointments: List<Appointment>,
    style: AppointmentStyle,
    isLast: Boolean
) {
    Column(
        modifier = Modifier.padding(top = 15.dp)
    ) {
        Text(
            text = title,
            fontSize = 12.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(top = 15.dp, bottom = if(isLast) 50.dp else 0.dp)
        ){
            items(appointments) { appointment ->
                AppointmentItemView(
                    appointment = appointment,
                    style = style
                )
            }
        }
    }
    
}


@Composable
fun EmptyAppointmentListView(
    message: String = "No cuenta con citas programadas.",
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = message,
            fontSize = 12.sp,
            color = BlackGreen
        )
    }
}

@Preview
@Composable
fun AppointmentPreView() {
    AppointmentView(navController = rememberNavController())
}