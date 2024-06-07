package com.mentalhealth.eifie.ui.view.appointment.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.datetimepicker.EDateField
import com.mentalhealth.eifie.ui.common.datetimepicker.ETimeField
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.layout.HeaderComponent
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.White95
import com.mentalhealth.eifie.util.ERR_REGISTER
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules

@Composable
fun AppointmentRegisterView(
    navController: NavHostController?,
    viewModel: AppointmentRegisterViewModel?,
    patient: Pair<Long, MutableLiveData<String>>?
) {

    val validForm = viewModel?.validForm?.collectAsStateWithLifecycle()?.value ?: false
    val state = viewModel?.state?.collectAsStateWithLifecycle()?.value ?: ViewState.Idle

    LaunchedEffect(patient) {
        viewModel?.setFormValue((patient?.first ?: -1).toString(), FormField.PATIENT)
    }

    Box (
        modifier = Modifier.background(color = CustomWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 35.dp, horizontal = 24.dp)
        ) {
            HeaderComponent(
                title = stringResource(id = R.string.appointment),
                subtitle = stringResource(id = R.string.book_appointment),
                onBack = { navController?.popBackStack() }
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ETextField(
                        values = TextFieldValues(
                            label = stringResource(id = R.string.patient),
                            initialValue = patient?.second?.value ?: "",
                            icon = EIcon(icon = Icons.Default.Search),
                            type = TextFieldType.LABELED_SUFFIX_ICON,
                            borderColor = BlackGreen,
                            enabled = false,
                            onValueChange = {  },
                            isValid = {
                                (ValidateText(it.text) checkWith defaultRules).let { result ->
                                    (result.exceptionOrNull()?.message ?: "") to result.isFailure
                                }
                            },
                            modifier = Modifier
                                .padding(top = 25.dp)
                                .fillMaxWidth()
                                .clickable {
                                    navController?.navigate(Router.APPOINTMENT_SEARCH_PATIENT.route)
                                }
                        )
                    )
                    EDateField(
                        TextFieldValues(
                            label = stringResource(id = R.string.appointment_date),
                            icon = EIcon(icon = Icons.Default.DateRange),
                            color = BlackGreen,
                            onValueChange = { viewModel?.setFormValue(it.text, FormField.DATE) },
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
                    ETimeField(
                        TextFieldValues(
                            label = stringResource(id = R.string.init_hour),
                            icon = EIcon(icon = Icons.Filled.AccessTime),
                            color = BlackGreen,
                            onValueChange = { viewModel?.setFormValue(it.text, FormField.TIME) },
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
                    ETextField(
                        values = TextFieldValues(
                            label = stringResource(id = R.string.reason),
                            icon = EIcon(icon = Icons.Default.Lock),
                            type = TextFieldType.LABELED,
                            radius = 30.0,
                            borderColor = BlackGreen,
                            minLines = 3,
                            onValueChange = { viewModel?.setFormValue(it.text, FormField.REASON) },
                            isValid = {
                                (ValidateText(it.text) checkWith defaultRules).let { result ->
                                    (result.exceptionOrNull()?.message ?: "") to result.isFailure
                                }
                            },
                            modifier = Modifier
                                .padding(top = 25.dp)
                                .fillMaxWidth()
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    CancelButtonView(
                        onClick = { navController?.popBackStack() }
                    )
                    AcceptButtonView(
                        text = stringResource(id = R.string.schedule),
                        enabled = validForm,
                        onClick = { viewModel?.submitForm() }
                    )
                }
            }
        }

        HandleViewState(
            navController = navController,
            state = state
        )
    }
}


@Composable
fun HandleViewState(
    state: ViewState,
    navController: NavHostController?
) {
    when(state) {
        ViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White95)
            )
        }
        is ViewState.Success -> {
            EAnimation(
                resource = R.raw.success_animation,
                iterations = 1,
                actionOnEnd = {
                    navController?.popBackStack()
                },
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White95)
            )
        }
        is ViewState.Error -> state.run {
            EDialogError(
                title = ERR_REGISTER,
                message = message,
                onCancelText = stringResource(id = R.string.go_back_login),
                onCancel = { },
                onDismissRequest = {  }
            )
        }
        else -> Unit
    }

}

@Preview
@Composable
fun AppointmentRegisterPreview() {
    AppointmentRegisterView(navController = null, viewModel = null, patient = null)
}


