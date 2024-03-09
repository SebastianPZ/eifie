package com.mentalhealth.eifie.ui.common.textfield

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import com.mentalhealth.eifie.ui.common.dropdown.DropdownItem
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.util.emptyString

data class TextFieldValues(
    val initialValue: String = "",
    val placeholder: String = emptyString(),
    val label: String? = null,
    val radius: Double? = null,
    val icon: EIcon? = null,
    val onValueChange: (it: TextFieldValue) -> Unit = { },
    val isValid: ((it: TextFieldValue) -> Pair<String, Boolean>)? = null,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val borderColor: Color = Color.Transparent,
    val color: Color = Color.Transparent,
    val type: TextFieldType = TextFieldType.LABELED,
    val inputType: InputType = InputType.DEFAULT,
    @SuppressLint("ModifierParameter") val modifier: Modifier = Modifier
)

data class DropdownFieldValues(
    val placeholder: String = emptyString(),
    val items: List<DropdownItem>,
    val initialValue: Int? = null,
    val label: String? = null,
    val radius: Double? = null,
    val color: Color = DarkGray,
    val borderColor: Color = DarkGray,
    val onValueChange: (it: Int) -> Unit = { },
    @SuppressLint("ModifierParameter") val modifier: Modifier = Modifier,
    @SuppressLint("ModifierParameter") val dropdownModifier: Modifier = Modifier
)

data class EIcon(
    val icon: ImageVector,
    val description: String = "icon"
)


enum class TextFieldType {
    LABELED,
    LABELED_ICON,
    NO_LABELED,
    NO_LABELED_ICON
}

enum class InputType {
    DEFAULT,
    PASSWORD
}

