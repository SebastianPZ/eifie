package com.mentalhealth.eifie.ui.register.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.data.database.entities.Hospital
import com.mentalhealth.eifie.domain.entities.models.PersonalData
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.datetimepicker.EDateField
import com.mentalhealth.eifie.ui.common.dropdown.DropdownItem
import com.mentalhealth.eifie.ui.common.dropdown.EDropdownField
import com.mentalhealth.eifie.ui.common.textfield.DropdownFieldValues
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.register.role.RoleOption
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules

@Composable
fun RegisterPersonalComponent(
    personalData: PersonalData,
    initHospitals: () -> Unit = {},
    hospitals: List<DropdownItem> = listOf(),
    onBack: () -> Unit,
    onContinue: (PersonalData) -> Unit,
    role: RoleOption?
) {

    val isValid = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isValid.value = personalData.isValid()

        if(role?.id == Role.PSYCHOLOGIST.ordinal) initHospitals()
    }

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
                values =  TextFieldValues(
                    initialValue = personalData.firstname,
                    label = stringResource(id = R.string.name),
                    icon = EIcon(icon = Icons.Default.Lock),
                    type = TextFieldType.LABELED,
                    borderColor = BlackGreen,
                    onValueChange =  {
                        personalData.firstname = it.text
                        isValid.value = personalData.isValid()
                    },
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
                    initialValue = personalData.lastname,
                    label = stringResource(id = R.string.last_name),
                    icon = EIcon(icon = Icons.Default.Lock),
                    type = TextFieldType.LABELED,
                    borderColor = BlackGreen,
                    onValueChange = {
                        personalData.lastname = it.text
                        isValid.value = personalData.isValid()
                    },
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
                    initialValue = personalData.birthdate,
                    label = stringResource(id = R.string.birth_date),
                    icon = EIcon(icon = Icons.Default.DateRange),
                    color = BlackGreen,
                    onValueChange = {
                        personalData.birthdate = it.text
                        isValid.value = personalData.isValid()
                    },
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
            SelectHospital(hospitals, personalData, role)
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
                enabled = isValid.value
            ) {
                onContinue(personalData)
            }
        }
    }
}

@Composable
fun SelectHospital(
    hospitals: List<DropdownItem>,
    personalData: PersonalData,
    role: RoleOption?
) {

    when(role?.id) {
        Role.PSYCHOLOGIST.ordinal -> {
            EDropdownField(
                values = DropdownFieldValues(
                    initialValue = personalData.hospital,
                    label = "Hospital",
                    items = hospitals,
                    onValueChange = { personalData.hospital = it },
                    modifier = Modifier
                        .padding(top = 20.dp,)
                        .fillMaxWidth(),
                    dropdownModifier = Modifier
                        .padding(horizontal = 24.dp)
                )
            )
        }
        else -> Unit
    }
}