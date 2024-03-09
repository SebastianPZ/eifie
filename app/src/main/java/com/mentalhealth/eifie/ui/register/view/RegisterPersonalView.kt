package com.mentalhealth.eifie.ui.register.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.common.datepicker.EDateField
import com.mentalhealth.eifie.ui.common.dropdown.EDropdownField
import com.mentalhealth.eifie.ui.common.textfield.DropdownFieldValues
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.register.RegisterViewModel
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules

@Composable
fun RegisterPersonalData(
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
                values =  TextFieldValues(
                    initialValue = viewModel.user.firstName,
                    label = stringResource(id = R.string.name),
                    icon = EIcon(icon = Icons.Default.Lock),
                    type = TextFieldType.LABELED,
                    borderColor = DarkGray,
                    onValueChange =  { viewModel.setFormValue(it.text, FormField.FIRSTNAME) },
                    isValid = {
                        (ValidateText(it.text) checkWith defaultRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            )
            ETextField(
                values = TextFieldValues(
                    initialValue = viewModel.user.lastName,
                    label = stringResource(id = R.string.last_name),
                    icon = EIcon(icon = Icons.Default.Lock),
                    type = TextFieldType.LABELED,
                    borderColor = DarkGray,
                    onValueChange = { viewModel.setFormValue(it.text, FormField.LASTNAME) },
                    isValid = {
                        (ValidateText(it.text) checkWith defaultRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                )
            )
            EDateField(
                TextFieldValues(
                    initialValue = viewModel.user.birthDate,
                    label = stringResource(id = R.string.birth_date),
                    icon = EIcon(icon = Icons.Default.DateRange),
                    color = DarkGray,
                    onValueChange = { viewModel.setFormValue(it.text, FormField.BIRTHDATE) },
                    isValid = {
                        (ValidateText(it.text) checkWith defaultRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                )
            )
            SelectHospital(viewModel = viewModel)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 45.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.updateStep(navController, Step.ROLE_DATA)
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = DarkGray
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    tint = DarkGray
                )
                Text(
                    text = stringResource(id = R.string.go_back),
                    color = DarkGray,
                    modifier = Modifier
                        .padding(8.dp))
            }
            NextStepButton(navController, viewModel)
        }
    }
}

@Composable
fun SelectHospital(
    viewModel: RegisterViewModel
) {

    val role by viewModel.role.collectAsStateWithLifecycle()
    val hospitals by viewModel.hospitals.collectAsStateWithLifecycle()

    when(role?.id) {
        Role.PSYCHOLOGIST.ordinal -> {
            EDropdownField(
                values = DropdownFieldValues(
                    initialValue = viewModel.user.hospital,
                    label = "Hospital",
                    items = hospitals,
                    onValueChange = { viewModel.user.hospital = it },
                    modifier = Modifier
                        .padding(top = 20.dp, )
                        .fillMaxWidth(),
                    dropdownModifier = Modifier
                        .padding(horizontal = 24.dp)
                )
            )
        }
        else -> Unit
    }
}

@Composable
fun NextStepButton(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {

    val validForm by viewModel.validPersonal.collectAsStateWithLifecycle()

    Button(
        onClick = {
            viewModel.updateStep(navController, Step.USER_DATA)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGray
        ),
        enabled = validForm
    ) {
        Text(
            text = stringResource(id = R.string.carry_on),
            modifier = Modifier
                .padding(8.dp)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "")
    }
}