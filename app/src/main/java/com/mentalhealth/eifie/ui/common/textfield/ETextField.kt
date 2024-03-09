package com.mentalhealth.eifie.ui.common.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ETextField(
    values: TextFieldValues
) {
    when(values.type) {
        TextFieldType.LABELED -> {
            if(values.inputType == InputType.DEFAULT) ETextFieldLabeled(values = values)
            else ETextFieldPasswordLabeled(values = values)
        }
        TextFieldType.LABELED_ICON -> ETextFieldLabeledIcon(values = values)
        TextFieldType.NO_LABELED -> ETextFieldNoLabeled(values = values)
        TextFieldType.NO_LABELED_ICON -> {
            if(values.inputType == InputType.DEFAULT) ETextFieldNoLabeledIcon(values = values)
            else ETextFieldPasswordNoLabeledIcon(values = values)
        }
    }
}

@Composable
fun ETextFieldLabeled(
    values: TextFieldValues
) {
    var text by remember { mutableStateOf(TextFieldValue(values.initialValue)) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )

    if(isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ETextFieldPasswordLabeled(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var showText by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        visualTransformation = if (showText) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if(showText) IconButton(onClick = { showText = false }) {
                Icon(imageVector = Icons.Filled.Visibility, contentDescription = "")
            } else IconButton(onClick = { showText = true }) {
                Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "")
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )

    if(isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier
                .padding(top = 5.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth()
        )
    }

}

@Composable
fun ETextFieldLabeledIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let { Icon(imageVector = values.icon.icon, contentDescription = it.description) } },
        onValueChange = {
            text = it
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )
}

@Composable
fun ETextFieldNoLabeled(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        onValueChange = {
            text = it
            values.onValueChange(it) },
        keyboardOptions = values.keyboardOptions,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )
}

@Composable
fun ETextFieldNoLabeledIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let { Icon(imageVector = values.icon.icon, contentDescription = it.description) } },
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        isError = isError,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )

    if(isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier
                .padding(top = 5.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ETextFieldPasswordNoLabeledIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var showText by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let { Icon(imageVector = values.icon.icon, contentDescription = it.description) } },
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        visualTransformation = if (showText) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if(showText) IconButton(onClick = { showText = false }) {
                Icon(imageVector = Icons.Filled.Visibility, contentDescription = "")
            } else IconButton(onClick = { showText = true }) {
                Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "")
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = values.borderColor,
            unfocusedBorderColor = values.borderColor),
        modifier = values.modifier
    )

    if(isError) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier
                .padding(top = 5.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth()
        )
    }
}