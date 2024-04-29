package com.mentalhealth.eifie.ui.appointment.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.datetimepicker.EDateField
import com.mentalhealth.eifie.ui.common.datetimepicker.ETimeField
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.White95
import com.mentalhealth.eifie.util.ERR_REGISTER
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules

@Composable
fun AppointmentRegisterView(
    navController: NavHostController,
    viewModel: AppointmentRegisterViewModel = hiltViewModel<AppointmentRegisterViewModel>()
) {
    return Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.eifi_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 50.dp),
                text = stringResource(id = R.string.new_appointment),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White95,
                ),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                RegisterAppointmentForm(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

        HandleViewState(
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun RegisterAppointmentForm(
    navController: NavHostController,
    viewModel: AppointmentRegisterViewModel
) {

    val validForm by viewModel.validForm.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 25.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            EDateField(
                TextFieldValues(
                    label = stringResource(id = R.string.appointment_date),
                    icon = EIcon(icon = Icons.Default.DateRange),
                    color = BlackGreen,
                    onValueChange = { viewModel.setFormValue(it.text, FormField.DATE) },
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
                    onValueChange = { viewModel.setFormValue(it.text, FormField.TIME) },
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
                    label = stringResource(id = R.string.patient),
                    icon = EIcon(icon = Icons.Default.Lock),
                    type = TextFieldType.LABELED,
                    borderColor = BlackGreen,
                    onValueChange = { viewModel.setFormValue(it.text, FormField.PATIENT) },
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
                .padding(vertical = 45.dp, horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            CancelButtonView(
                onClick = { navController.popBackStack() }
            )
            AcceptButtonView(
                isValidForm = validForm,
                onClick = { viewModel.submitForm() }
            )
        }
    }
}



@Composable
fun HandleViewState(
    viewModel: AppointmentRegisterViewModel,
    navController: NavHostController
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    when(state) {
        AppointmentRegisterViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            )
        }
        is AppointmentRegisterViewState.Success -> {
            EAnimation(
                resource = R.raw.success_animation,
                iterations = 1,
                actionOnEnd = {
                    navController.navigate(Router.REGISTER_SUCCESS.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            )
        }
        is AppointmentRegisterViewState.Error -> (state as AppointmentRegisterViewState.Error).run {
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
    AppointmentRegisterView(rememberNavController())
}