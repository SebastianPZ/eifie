package com.mentalhealth.eifie.ui.common.datetimepicker

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ETimePicker(
    onAccept: (Int, Int) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberTimePickerState()

    TimePickerDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = { onAccept(state.hour, state.minute) },
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(stringResource(id = R.string.accept))
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    ) {
        TimePicker(state = state)
    }
}

@Preview
@Composable
fun ETimePickerPreview() {
    ETimePicker(
        onAccept =  { _,_ -> },
        onCancel = {}
    )
}