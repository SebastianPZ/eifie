package com.mentalhealth.eifie.ui.register.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.InputType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.register.RegisterViewModel
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.util.ERR_SAME_TEXT
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.emailRules
import com.mentalhealth.eifie.util.passwordRules

@Composable
fun RegisterUserData(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    return Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 45.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            ETextField(
                TextFieldValues(
                    initialValue = viewModel.user.email,
                    label = stringResource(id = R.string.email),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = { viewModel.setFormValue(it.text, FormField.EMAIL) },
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
                    initialValue = viewModel.user.password,
                    label = stringResource(id = R.string.password),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = { viewModel.setFormValue(it.text, FormField.PASSWORD) },
                    isValid = {
                        (ValidateText(it.text) checkWith passwordRules).let { result ->
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
                    initialValue = viewModel.user.checkPassword,
                    label = stringResource(id = R.string.repeat_password),
                    icon = EIcon(icon = Icons.Default.Lock),
                    onValueChange = { viewModel.setFormValue(it.text, FormField.CHECK_PASSWORD) },
                    isValid = {
                        if(it.text == viewModel.user.password) "" to false
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
            OutlinedButton(
                onClick = {
                    viewModel.updateStep(navController, Step.PERSONAL_DATA)
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = BlackGreen
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(vertical = 45.dp, horizontal = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = BlackGreen
                )
                Text(
                    text = stringResource(id = R.string.go_back),
                    color = BlackGreen,
                    modifier = Modifier
                        .padding(8.dp))
            }
            RegisterButton(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun RegisterButton(
    viewModel: RegisterViewModel,
    navController: NavHostController,
) {
    val validForm by viewModel.validUser.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsStateWithLifecycle()

    Button(
        onClick = {
            when(role?.id) {
                Role.PATIENT.ordinal -> {
                    viewModel.updateStep(navController, Step.PSYCHOLOGIST)
                }
                Role.PSYCHOLOGIST.ordinal -> {
                    viewModel.registerUser()
                }
                else -> Unit
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = BlackGreen
        ),
        enabled = validForm,
        modifier = Modifier
            .padding(vertical = 45.dp, horizontal = 24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.register_button),
            modifier = Modifier
                .padding(8.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "")
    }
}