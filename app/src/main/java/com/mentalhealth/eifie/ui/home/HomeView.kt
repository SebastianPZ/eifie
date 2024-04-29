package com.mentalhealth.eifie.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mentalhealth.eifie.domain.entities.models.Patient
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.main.ConfigViewModel
import com.mentalhealth.eifie.ui.patient.PatientListView

@Composable
fun HomeView(
    configViewModel: ConfigViewModel = hiltViewModel<ConfigViewModel>()
) {

    val role by configViewModel.userRole.collectAsStateWithLifecycle()
    val user by configViewModel.userName.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        Text(
            text = "Hola, ${user}!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(
            text = if(role == Role.PATIENT) "Actividades" else "Pacientes",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        if(role == Role.PATIENT) {
            Unit
        } else {
            PatientListView(
                patientList = listOf(
                    Patient(0, "Luis Lapiedra", "10 semanas", "En Progreso"),
                    Patient(0, "Luis Lapiedra", "10 semanas", "En Progreso")
                ),
                modifier = Modifier.padding(top = 34.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomePreView(){
    HomeView()
}