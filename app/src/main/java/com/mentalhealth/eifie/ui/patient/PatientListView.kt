package com.mentalhealth.eifie.ui.patient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.domain.entities.models.Patient

@Composable
fun PatientListView(
    patientList: List<Patient>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.fillMaxSize()
    ) {
        items(patientList.size) { index ->
            PatientItemView(patient = patientList[index])
            if(index < patientList.lastIndex)
                HorizontalDivider(modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 5.dp)
                    .fillMaxWidth()
                    .width(1.dp))
        }
    }
}