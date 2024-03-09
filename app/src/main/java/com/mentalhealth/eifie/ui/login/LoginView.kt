package com.mentalhealth.eifie.ui.login

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.RegisterActivity
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.textfield.EIcon
import com.mentalhealth.eifie.ui.common.textfield.ETextField
import com.mentalhealth.eifie.ui.common.textfield.InputType
import com.mentalhealth.eifie.ui.common.textfield.TextFieldValues
import com.mentalhealth.eifie.ui.common.textfield.TextFieldType
import com.mentalhealth.eifie.ui.theme.DarkGray
import com.mentalhealth.eifie.ui.theme.EifieTheme
import com.mentalhealth.eifie.ui.theme.White50
import com.mentalhealth.eifie.ui.theme.White60
import com.mentalhealth.eifie.ui.theme.White85
import com.mentalhealth.eifie.ui.theme.cutiveFontFamily
import com.mentalhealth.eifie.util.ERR_LOGIN
import com.mentalhealth.eifie.util.FormField
import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.defaultRules
import com.mentalhealth.eifie.util.emailRules

@Composable
fun Login(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()
) {

    val context = LocalContext.current

    val welcomeText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontSize = TextUnit(14F, TextUnitType.Sp),
            fontFamily = cutiveFontFamily,
            fontWeight = FontWeight.Normal
        )) {
            append(stringResource(id = R.string.welcome_title))
        }
        withStyle(style = SpanStyle(
            fontSize = TextUnit(26F, TextUnitType.Sp),
            fontWeight = FontWeight.Bold
        )) {
            append(stringResource(id = R.string.welcome_subtitle))
        }
    }

    return Box(
        modifier = Modifier
            .fillMaxWidth()
            .paint(
                painter = painterResource(id = R.drawable.eifi_background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 45.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = welcomeText,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 1.8.em
                    )
                )
            )
            Column {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = White60,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    ETextField(
                        values = TextFieldValues(
                            placeholder = stringResource(id = R.string.email),
                            icon = EIcon(icon = Icons.Default.Person),
                            type = TextFieldType.NO_LABELED_ICON,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            borderColor = White50,
                            onValueChange =  { viewModel.setFormValue(it.text, FormField.EMAIL) },
                            isValid = {
                                (ValidateText(it.text) checkWith emailRules).let { result ->
                                    (result.exceptionOrNull()?.message ?: "") to result.isFailure
                                }
                            },
                            modifier = Modifier
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                        )
                    )
                    ETextField(
                        values = TextFieldValues(
                            placeholder = stringResource(id = R.string.password),
                            icon = EIcon(icon = Icons.Default.Lock),
                            type = TextFieldType.NO_LABELED_ICON,
                            inputType = InputType.PASSWORD,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            borderColor = White50,
                            onValueChange =  { viewModel.setFormValue(it.text, FormField.PASSWORD) },
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
                    LoginButton(viewModel = viewModel)
                }
                Button(
                    onClick = {
                        context.startActivity(Intent(context, RegisterActivity::class.java))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.register_button),
                        color = DarkGray,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
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
    viewModel: LoginViewModel,
    navigateToHome: () -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    when(state) {
        LoginViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = White85)
            )
        }
        is LoginViewState.Success -> {
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
                    .background(color = White85)
            )
        }
        is LoginViewState.Error -> (state as LoginViewState.Error).run {
            EDialogError(
                title = ERR_LOGIN,
                message = message) {
                viewModel.setInitialLoginViewStatus()
            }
        }
        else -> Unit
    }


}

@Composable
fun LoginButton(viewModel: LoginViewModel) {

    val validForm by viewModel.validForm.collectAsStateWithLifecycle()

    Button(
        onClick = {
            viewModel.loginUser()
        },
        enabled = validForm,
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkGray
        ),
        modifier = Modifier
            .padding(top = 45.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.login_button),
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    EifieTheme {
        //Login()
    }
}