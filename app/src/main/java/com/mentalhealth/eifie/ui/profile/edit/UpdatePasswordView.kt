package com.mentalhealth.eifie.ui.profile.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.layout.DefaultViewLayout
import com.mentalhealth.eifie.ui.common.layout.HeaderComponent
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.InputType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.login.Loading
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.util.ERR_SAME_TEXT
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules
import com.mentalhealth.eifie.util.passwordRules

@Composable
fun UpdatePasswordView(
    navController: NavController?,
    viewModel: UpdatePasswordViewModel?
) {
    val validNewPassword = remember { mutableStateOf(false) }
    val validConfirmPassword = remember { mutableStateOf(false) }
    val validActualPassword = remember { mutableStateOf(false) }

    val actualPassword = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val state = viewModel?.state?.collectAsStateWithLifecycle()

    DefaultViewLayout(
        header = {
            HeaderComponent(
                title = "Actualizar contraseña",
                subtitle = "Seguridad",
                onBack = { navController?.popBackStack() },
                modifier = Modifier.padding(top = 35.dp, start = 24.dp, end = 24.dp)
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 45.dp, horizontal = 24.dp)
        ) {
            ETextField(
                TextFieldValues(
                    label = "Contraseña actual",
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = {
                        actualPassword.value = it.text
                    },
                    isValid = {
                        (ValidateText(it.text) checkWith defaultRules).let { result ->
                            validActualPassword.value = result.isSuccess
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    type = TextFieldType.LABELED,
                    inputType = InputType.PASSWORD,
                    borderColor = BlackGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            )
            ETextField(
                TextFieldValues(
                    label = "Contraseña nueva",
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = {
                        newPassword.value = it.text
                    },
                    isValid = {
                        (ValidateText(it.text) checkWith passwordRules).let { result ->
                            validNewPassword.value = result.isSuccess
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    type = TextFieldType.LABELED,
                    inputType = InputType.PASSWORD,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    borderColor = BlackGreen,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                )
            )
            ETextField(
                TextFieldValues(
                    enabled = validNewPassword.value,
                    compareValue = actualPassword.value,
                    label = "Repetir contraseña nueva",
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = { },
                    isValid = {
                        if(it.text == newPassword.value) {
                            validConfirmPassword.value = true
                            "" to false
                        }
                        else {
                            validConfirmPassword.value = false
                            ERR_SAME_TEXT to true
                        }
                    },
                    type = TextFieldType.LABELED,
                    inputType = InputType.PASSWORD,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    borderColor = BlackGreen,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                )
            )
            Button(
                onClick = {
                    viewModel?.updatePassword(actualPassword.value, newPassword.value)
                },
                enabled = validActualPassword.value && validNewPassword.value && validConfirmPassword.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(vertical = 45.dp, horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Actualizar",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }

    Loading(
        state = state?.value,
        successAnimation = R.raw.success_animation,
        onSuccess = { navController?.popBackStack() }
    ) {
        EDialogError(
            title = "Error al actualizar contraseña",
            message = it,
            onCancelText = "Cancelar",
            onSuccess = {
                viewModel?.resolveDialog()
            },
            onCancel = { navController?.popBackStack() }
        ) { }
    }
}

@Preview
@Composable
fun UpdatePasswordPreview() {
    UpdatePasswordView(null, null)
}