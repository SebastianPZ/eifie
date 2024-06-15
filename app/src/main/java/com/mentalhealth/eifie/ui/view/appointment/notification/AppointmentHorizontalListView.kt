package com.mentalhealth.eifie.ui.view.appointment.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.Appointment

@Composable
 fun AppointmentHorizontalListView(
     appointments: List<Appointment>,
     modifier: Modifier = Modifier,
     emptyText: String = stringResource(id = R.string.empty_soon_appointments)
 ) {
    if(appointments.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = emptyText,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize()
        ) {
            items(appointments.size) { index ->
                AppointmentHorizontalItem(
                    appointment = appointments[index]
                )
                if(index < appointments.lastIndex)
                    Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
 }

@Preview
@Composable
fun AppointmentHorizontalListPreview() {
    AppointmentHorizontalListView(appointments = listOf())
}