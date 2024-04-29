package com.mentalhealth.eifie.ui.patient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.models.Patient
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.SkyBlue

@Composable
fun PatientItemView(
    patient: Patient
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserPhotoView(
                modifier = Modifier.size(50.dp),
                fontSize = 14.sp
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = patient.username, fontSize = 14.sp, color = BlackGreen)
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = patient.time, fontSize = 12.sp, color = LightGray)
            }
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = SkyBlue,
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = patient.state,
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp))
        }
    }
}

@Preview
@Composable
fun PatientItemPreView() {
    PatientItemView(
        Patient(0, "Raul Cossio", "10 semanas", "En Progreso")
    )
}