package com.mentalhealth.eifie.ui.common.datetimepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.common.textfield.IconField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues

@Composable
fun ETimeField(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue(values.initialValue)) }
    var showTimeDialog by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        )
        },
        label = { values.label?.let { Text(text = it) } },
        trailingIcon = { values.icon?.let{ IconField(icon = it) }},
        onValueChange = {
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            text = it
        },
        readOnly = true,
        enabled = false,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = values.color,
            disabledPlaceholderColor = values.color,
            disabledTrailingIconColor = values.color,
            disabledBorderColor = values.color,
            focusedBorderColor = values.color,
            unfocusedBorderColor = values.color,
            disabledLabelColor = values.color,
            focusedLabelColor = values.color,
            unfocusedLabelColor = values.color),
        isError = isError,
        modifier = values.modifier
            .clickable {
                showTimeDialog = true
            }
    )

    if (showTimeDialog) {
        ETimePicker(
            onAccept = { hour, minute ->
                text = TextFieldValue("$hour:$minute")
                values.onValueChange(text)
                showTimeDialog = false // close dialog
            },
            onCancel = {
                showTimeDialog = false //close dialog
            }
        )
    }
}