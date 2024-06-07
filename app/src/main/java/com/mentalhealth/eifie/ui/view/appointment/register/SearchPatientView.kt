package com.mentalhealth.eifie.ui.view.appointment.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.view.patient.PatientItem
import com.mentalhealth.eifie.ui.viewmodel.SearchPatientViewModel

@Composable
fun SearchPatientView(
    navController: NavHostController?,
    viewModel: SearchPatientViewModel?
) {

    val patients = viewModel?.patients?.collectAsStateWithLifecycle()

    Box (
        modifier = Modifier
            .background(color = CustomWhite)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 35.dp)
        ) {
            ETextField(
                values = TextFieldValues(
                    placeholder = "Buscar",
                    icon = EIcon(icon = Icons.Default.Search),
                    type = TextFieldType.NO_LABELED_SUFFIX_ICON,
                    borderColor = BlackGreen,
                    onValueChange = { viewModel?.search(it.text) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
            )
            if(patients?.value?.isEmpty() != false) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_patients),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn (
                    modifier = Modifier.weight(1f).padding(vertical = 30.dp)
                ) {
                    items(patients.value.size) { index ->
                        PatientItem(
                            patient = patients.value[index],
                            onClick = {
                                navController?.run {
                                    previousBackStackEntry?.savedStateHandle?.set("patientName", it.username)
                                    previousBackStackEntry?.savedStateHandle?.set("patientId", it.id)
                                    popBackStack()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CancelButtonView(
                    onClick = {
                        navController?.run {
                            popBackStack()
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchPatientPreview() {
    SearchPatientView(navController = null, viewModel = null)
}