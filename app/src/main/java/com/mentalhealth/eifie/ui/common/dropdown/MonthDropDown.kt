package com.mentalhealth.eifie.ui.common.dropdown

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.ui.common.textfield.DropdownFieldValues
import com.mentalhealth.eifie.util.emptyString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EMonthDropdown(
    values: DropdownFieldValues
) {
    fun handleFirstItem(): String {
        return if(values.items.isNotEmpty()) {
            values.items.find { it.id == values.initialValue }?.text ?: values.items.first().text
        }
        else emptyString()
    }

    var selectedText by remember { mutableStateOf(handleFirstItem()) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selectedText,
            placeholder = { Text(
                text = values.placeholder,
                fontWeight = FontWeight.Light
            ) },
            label = { values.label?.let { Text(text = it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = expanded
            ) },
            onValueChange = {},
            readOnly = true,
            enabled = false,
            shape = RoundedCornerShape((values.radius ?: 50.0).dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = values.color,
                disabledPlaceholderColor = values.color,
                disabledTrailingIconColor = values.color,
                disabledBorderColor = values.color,
                disabledLabelColor = values.color,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent),
            modifier = values.modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            values.items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.text) },
                    onClick = {
                        values.onValueChange(item.id)
                        selectedText = item.text
                        expanded = false
                    },
                    modifier = values.dropdownModifier
                )
            }
        }

    }
}