package com.mentalhealth.eifie.ui.common.datetimepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.common.textfield.IconField
import com.mentalhealth.eifie.util.toDateFormat
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.theme.CustomLightGray

@Composable
fun EDateField(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue(values.initialValue)) }
    var selectBirthDate by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    TextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        )},
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
        colors = TextFieldDefaults.colors(
            disabledTextColor = values.color,
            disabledPlaceholderColor = values.color,
            disabledTrailingIconColor = values.color,
            disabledLabelColor = values.color,
            focusedLabelColor = values.color,
            unfocusedLabelColor = values.color,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray,
            disabledContainerColor = CustomLightGray
        ),
        isError = isError,
        modifier = values.modifier
            .clickable {
                selectBirthDate = true
            }
    )

    if (selectBirthDate) {
        EDatePicker(
            onAccept = {
                text = TextFieldValue(it?.toDateFormat() ?: "")
                values.onValueChange(text)
                selectBirthDate = false // close dialog
            },
            onCancel = {
                selectBirthDate = false //close dialog
            }
        )
    }
}