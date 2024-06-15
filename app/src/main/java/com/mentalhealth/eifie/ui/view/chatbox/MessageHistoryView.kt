package com.mentalhealth.eifie.ui.view.chatbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.models.MessageUI
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.ui.view.appointment.notification.AppointmentHorizontalItem
import com.mentalhealth.eifie.ui.viewmodel.MessageHistoryViewModel

@Composable
fun MessageHistoryView(
    navController: NavHostController?,
    viewModel: MessageHistoryViewModel?
) {

    val state = viewModel?.state?.collectAsState()
    val messages = viewModel?.messages?.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 30.dp, horizontal = 1.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController?.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.width(17.dp))
            Text(
                text = "Historial Mensajes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        MessageListView(messages?.value ?: listOf())
    }


    when(state?.value) {
        ViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White90)
            )
        }
        else -> Unit
    }
}

@Composable
fun MessageListView(
    appointments: List<MessageUI>,
    modifier: Modifier = Modifier
) {
    if(appointments.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.empty_patient_messages),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            items(appointments.size) { index ->
                MessageHistoryBubble(
                    message = appointments[index],
                )
                Spacer(modifier = Modifier.size(15.dp))
            }
        }
    }
}