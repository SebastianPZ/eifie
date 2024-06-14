package com.mentalhealth.eifie.ui.view.appointment.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.ui.theme.Black10
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AppointmentHorizontalItem(
    appointment: Appointment
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .defaultMinSize(minWidth = 105.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                spotColor = Black10,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Row {
            Surface(
                color = LightSkyGray,
                shape = RoundedCornerShape(
                    topStart = 12.dp
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 70.dp, minHeight = 92.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = SimpleDateFormat("EEE", Locale("es", "PE")).format(appointment.date).uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlackGreen
                    )
                    Text(
                        text = SimpleDateFormat("dd", Locale("es", "PE")).format(appointment.date),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlackGreen
                    )
                    Text(
                        text = SimpleDateFormat("MMM", Locale("es", "PE")).format(appointment.date).uppercase(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlackGreen
                    )
                }
            }
            Column(
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 15.dp, end = 20.dp)
            ) {
                Text(
                    text = if(appointment.type == Role.PATIENT.ordinal) "Psicólogo" else "Paciente",
                    fontSize = 10.sp,
                    color = LightGray
                )
                Text(
                    text = if(appointment.type == Role.PATIENT.ordinal) appointment.psychologistName else appointment.patientName,
                    fontSize = 12.sp,
                    color = BlackGreen,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 6.dp, end = 20.dp)
            ) {
                Text(
                    text = "Hora",
                    fontSize = 10.sp,
                    color = LightGray
                )
                Text(
                    text = appointment.time,
                    fontSize = 12.sp,
                    color = BlackGreen
                )
            }
        }
    }
}

@Preview
@Composable
fun AppointmentHorizontalItemPreview() {
    AppointmentHorizontalItem(appointment = Appointment())
}