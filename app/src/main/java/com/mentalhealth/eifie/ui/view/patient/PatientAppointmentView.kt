package com.mentalhealth.eifie.ui.view.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.ui.view.appointment.notification.AppointmentHorizontalListView
import com.mentalhealth.eifie.ui.viewmodel.PatientAppointmentViewModel

@Composable
fun PatientAppointmentView(
    navController: NavHostController?,
    viewModel: PatientAppointmentViewModel?
) {

    val state = viewModel?.state?.collectAsStateWithLifecycle()
    val appointments = viewModel?.appointments?.collectAsStateWithLifecycle()

    Box (
        modifier = Modifier.background(CustomWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Row(
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
                    text = "Historial Citas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AppointmentHorizontalListView(
                appointments = appointments?.value ?: listOf(),
                modifier = Modifier.padding(vertical = 20.dp),
                emptyText = stringResource(id = R.string.empty_patient_appointments)
            )
        }
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

@Preview
@Composable
fun PatientAppointmentPreview() {
    PatientAppointmentView(navController = null, viewModel = null)
}