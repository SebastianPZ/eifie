package com.mentalhealth.eifie.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.util.ERR_LOGIN
import com.mentalhealth.eifie.util.ERR_RECOVER_PASSWORD
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.emailRules

@Composable
fun RecoverPasswordView(
    navController: NavController? = null,
    viewModel: RecoverPasswordViewModel? = null
) {
    val state = viewModel?.state?.collectAsStateWithLifecycle()

    RecoverPasswordForm(
        sendEmail = { email -> viewModel?.sendEmailToRecover(email) },
        goBack = { navController?.popBackStack() }
    )

    when(state?.value) {
        RecoverPasswordViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White90)
            )
        }
        is RecoverPasswordViewState.Success -> {
            EDialogError(
                animation = R.raw.email_success,
                title = "¡Correo enviado!",
                buttonText = "Aceptar",
                message = (state.value as RecoverPasswordViewState.Success).message) {
                navController?.popBackStack()
            }
        }
        is RecoverPasswordViewState.Error -> {
            EDialogError(
                title = ERR_RECOVER_PASSWORD,
                message = (state.value as RecoverPasswordViewState.Error).message,
                onCancelText = "Cancelar",
                onSuccess = {
                    viewModel.resolveDialog()
                },
                onCancel = { navController?.popBackStack() }
            ) { }
        }
        else -> Unit
    }

}

@Composable
fun RecoverPasswordForm(
    sendEmail: (String) -> Unit,
    goBack: () -> Unit
) {

    val email = remember { mutableStateOf("") }
    val validForm = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomWhite)
    ) {
        IconButton(
            onClick = goBack,
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 20.dp)
                .imePadding()
        ) {

            Image(painter = painterResource(id = R.drawable.eifi_logotype_h),
                contentDescription = "MoodMinder",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp)
            )
            Text(
                text = "Recuperar contraseña",
                color = BlackGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "Ingrese el correo eléctronico asociado a su cuenta para enviar un correo con la nueva contraseña provisional (Puede modificarlo despues desde su perfil).",
                color = BlackGreen,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            ETextField(
                values = TextFieldValues(
                    placeholder = stringResource(id = R.string.email),
                    icon = EIcon(painter = painterResource(id = R.drawable.ic_user)),
                    type = TextFieldType.NO_LABELED_ICON,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange =  { email.value = it.text },
                    isValid = {
                        (ValidateText(it.text) checkWith emailRules).let { result ->
                            validForm.value = result.isSuccess
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 25.dp, horizontal = 5.dp)
                        .fillMaxWidth()
                )
            )
            Button(
                onClick = {
                    sendEmail(email.value)
                },
                enabled = validForm.value,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Enviar",
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun RecoverPasswordPreview() {
    RecoverPasswordView()
}