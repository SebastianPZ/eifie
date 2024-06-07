package com.mentalhealth.eifie.ui.login

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.RegisterActivity
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.InputType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.White50
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.util.ERR_LOGIN
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules
import com.mentalhealth.eifie.util.emailRules

@Composable
fun LoginView(
    navController: NavHostController?,
    viewModel: LoginViewModel?,
    navigateToHome: () -> Unit
) {

    val context = LocalContext.current
    val validForm = viewModel?.validForm?.collectAsStateWithLifecycle()

    return Box(
        modifier = Modifier
            .fillMaxSize().background(color = CustomWhite)
    ) {
        IconButton(
            onClick = { navController?.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 15.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .imePadding()
        ) {
            Image(painter = painterResource(id = R.drawable.eifi_logotype_h),
                contentDescription = "MoonMinder",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(250.dp)
            )
            ETextField(
                values = TextFieldValues(
                    placeholder = stringResource(id = R.string.email),
                    icon = EIcon(painter = painterResource(id = R.drawable.ic_user)),
                    type = TextFieldType.NO_LABELED_ICON,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange =  { viewModel?.setFormValue(it.text, FormField.EMAIL) },
                    isValid = {
                        (ValidateText(it.text) checkWith emailRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .padding(top = 25.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                )
            )
            ETextField(
                values = TextFieldValues(
                    placeholder = stringResource(id = R.string.password),
                    icon = EIcon(painter = painterResource(id = R.drawable.ic_lock)),
                    type = TextFieldType.NO_LABELED_ICON,
                    inputType = InputType.PASSWORD,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    borderColor = White50,
                    onValueChange =  { viewModel?.setFormValue(it.text, FormField.PASSWORD) },
                    isValid = {
                        (ValidateText(it.text) checkWith defaultRules).let { result ->
                            (result.exceptionOrNull()?.message ?: "") to result.isFailure
                        }
                    },
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                )
            )
            Button(
                onClick = {
                    viewModel?.loginUser()
                },
                enabled = validForm?.value ?: false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(vertical = 30.dp, horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "¿No tienes cuenta?")
                VerticalDivider(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.register_button),
                    color = DarkGreen,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, RegisterActivity::class.java))
                    }
                )
            }
        }
        Loading(
            viewModel = viewModel,
            navigateToHome = navigateToHome
        )
    }
}

@Composable
fun Loading(
    viewModel: LoginViewModel?,
    navigateToHome: () -> Unit,
) {

    val state = viewModel?.state?.collectAsStateWithLifecycle()

    when(state?.value) {
        ViewState.Loading -> {
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
        is ViewState.Success -> {
            EAnimation(
                resource = R.raw.unlock_animation,
                iterations = 1,
                actionOnEnd = {
                    navigateToHome.invoke()
                },
                animationModifier = Modifier
                    .size(200.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White90)
            )
        }
        is ViewState.Error -> (state.value as ViewState.Error).run {
            EDialogError(
                title = ERR_LOGIN,
                message = message) {
                viewModel.setInitialLoginViewStatus()
            }
        }
        else -> Unit
    }


}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginView(null, viewModel = null, navigateToHome = {  })
}