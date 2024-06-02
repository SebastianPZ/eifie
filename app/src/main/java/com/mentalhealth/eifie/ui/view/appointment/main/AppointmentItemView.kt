package com.mentalhealth.eifie.ui.view.appointment.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.Appointment
import com.mentalhealth.eifie.domain.entities.AppointmentStyle
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.ui.theme.Black10
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AppointmentItemView(
    appointment: Appointment,
    style: AppointmentStyle
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .defaultMinSize(minWidth = 105.dp, minHeight = 176.dp)
            .width(105.dp)
            .shadow(
                elevation = 8.dp,
                spotColor = Black10,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = style.containerColor,
            ),
            shape = RoundedCornerShape(
                topStart = 12.dp, topEnd = 12.dp
            ),
            modifier = Modifier
                .defaultMinSize(minWidth = 105.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = SimpleDateFormat("EEE", Locale("es", "PE")).format(appointment.date).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = style.textColor
                )
                Text(
                    text = SimpleDateFormat("dd", Locale("es", "PE")).format(appointment.date),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = style.textColor
                )
                Text(
                    text = SimpleDateFormat("MMMM", Locale("es", "PE")).format(appointment.date).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = style.textColor
                )
            }
        }

        Column(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, bottom = 6.dp, end = 12.dp)
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
            modifier = Modifier.padding(top = 6.dp, start = 12.dp, bottom = 15.dp, end = 12.dp)
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


@Preview
@Composable
fun AppointmentItemPreView() {
    AppointmentItemView(
        Appointment(
            psychologistFirstName = "Luis",
            psychologistLastName = "Piedra",
            time = "10:00 am"
        ),
        style = AppointmentStyle("", Color.White, DarkGreen)
    )
}