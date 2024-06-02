package com.mentalhealth.eifie.ui.view.patient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.domain.entities.Patient
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.SkyBlue

@Composable
fun PatientItem(
    patient: Patient?,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserPhotoView(
                    username = patient?.username,
                    modifier = Modifier.size(50.dp),
                    fontSize = 14.sp
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(text = patient?.lastname ?: "", fontSize = 14.sp, color = BlackGreen, lineHeight = 14.sp)
                    Text(text = patient?.firstname ?: "", fontSize = 12.sp, color = LightGray)
                }
            }
            Surface(
                color = SkyBlue,
                shape = RoundedCornerShape(20.dp),
            ) {
                Text(
                    text = patient?.state ?: "",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PatientItemPreView() {
    PatientItem(
        Patient(0, "Luis", "Lapiedra", "Luis Lapiedra", "Ok"),
        {}
    )
}