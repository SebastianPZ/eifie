package com.mentalhealth.eifie.ui.register.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.UserData
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.InputType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.util.ERR_SAME_TEXT
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.emailRules
import com.mentalhealth.eifie.util.passwordRules

@Composable
fun RegisterUserComponent(
    userData: UserData,
    onBack: () -> Unit = {},
    onContinue: (UserData) -> Unit = {}
) {

    val isValid = remember { mutableStateOf(false) }
    val enableConfirmPassword = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    Log.d("Confirm password habilitado => $enableConfirmPassword", enableConfirmPassword.toString())

    return Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 45.dp, horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ETextField(
                TextFieldValues(
                    initialValue = userData.email,
                    label = stringResource(id = R.string.email),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = {
                        userData.email = it.text
                        isValid.value = userData.isValid(password.value)
                    },
                    isValid = {
                        (ValidateText(it.text) checkWith emailRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    type = TextFieldType.LABELED,
                    borderColor = BlackGreen,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            )
            ETextField(
                TextFieldValues(
                    initialValue = userData.password,
                    label = stringResource(id = R.string.password),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = {
                        password.value = it.text
                        isValid.value = userData.isValid(password.value)
                    },
                    isValid = {
                        (ValidateText(it.text) checkWith passwordRules).let { result ->
                            if(result.isSuccess) enableConfirmPassword.value = true
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
                    enabled = enableConfirmPassword.value,
                    compareValue = password.value,
                    initialValue = userData.confirmPassword,
                    label = stringResource(id = R.string.repeat_password),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = {
                        userData.confirmPassword = it.text
                        isValid.value = userData.isValid(password.value)
                    },
                    isValid = {
                        if(it.text == password.value) "" to false
                        else ERR_SAME_TEXT to true
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
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CancelButtonView {
                onBack()
            }
            AcceptButtonView(
                enabled = isValid.value,
                text = stringResource(id = R.string.register_button)
            ) {
                onContinue(userData.apply { this.password = password.value })
            }
        }
    }
}