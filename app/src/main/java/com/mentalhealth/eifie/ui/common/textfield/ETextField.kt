package com.mentalhealth.eifie.ui.common.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomLightGray
import com.mentalhealth.eifie.ui.theme.DarkGreen

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
        TextFieldType.LABELED_SUFFIX_ICON -> ETextFieldLabeledSuffixIcon(values = values)
        TextFieldType.NO_LABELED -> ETextFieldNoLabeled(values = values)
        TextFieldType.NO_LABELED_SUFFIX_ICON -> ETextFieldNoLabeledSuffixIcon(values = values)
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

    TextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        minLines = values.minLines,
        maxLines = values.maxLines,
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
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

    LaunchedEffect(values.compareValue) {
        if(values.compareValue != null) {
            values.isValid?.let { validate -> validate(text) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
        }
    }

    TextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        enabled = values.enabled,
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray,
            disabledLabelColor = BlackGreen,
            disabledTrailingIconColor = BlackGreen
        ),
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
fun ETextFieldLabeledIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let{ IconField(icon = it) }},
        onValueChange = {
            text = it
            values.onValueChange(it) },
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
        modifier = values.modifier
    )
}

@Composable
fun ETextFieldLabeledSuffixIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue(values.initialValue)) }

    TextField(
        value = text,
        label = { values.label?.let { Text(text = it) } },
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        trailingIcon = { values.icon?.let{ IconField(icon = it) }},
        onValueChange = {
            text = it
            values.onValueChange(it) },
        enabled = values.enabled,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray,
            disabledContainerColor = CustomLightGray,
            disabledLabelColor = BlackGreen,
            disabledTrailingIconColor = BlackGreen
        ),
        modifier = values.modifier
    )
}

@Composable
fun ETextFieldNoLabeled(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
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

    TextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let{ IconField(icon = it) }},
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        isError = isError,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
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
fun ETextFieldNoLabeledSuffixIcon(
    values: TextFieldValues
) {

    var text by remember { mutableStateOf(TextFieldValue(values.initialValue)) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    TextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        trailingIcon = { values.icon?.let{ IconField(icon = it) }},
        onValueChange = {
            text = it
            values.isValid?.let { validate -> validate(it) }?.let { error ->
                errorMessage = error.first
                isError = error.second
            }
            values.onValueChange(it) },
        isError = isError,
        shape = RoundedCornerShape((values.radius ?: 50.0).dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
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

    TextField(
        value = text,
        placeholder = { Text(
            text = values.placeholder,
            fontWeight = FontWeight.Light
        ) },
        leadingIcon = { values.icon?.let{ IconField(icon = it) }},
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = CustomLightGray,
            unfocusedContainerColor = CustomLightGray
        ),
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
fun IconField(icon: EIcon?): Unit {
    when {
        icon?.icon != null -> Icon(
            imageVector = icon.icon,
            contentDescription = icon.description,
            modifier = Modifier.size(18.dp)
        )
        icon?.painter != null -> Icon(
            painter = icon.painter,
            contentDescription = icon.description,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    ETextField(
        values = TextFieldValues(
            initialValue = "Prueba",
            label = "Prueba",
            type = TextFieldType.LABELED_SUFFIX_ICON,
            borderColor = DarkGreen,
            enabled = false
        )
    )
}