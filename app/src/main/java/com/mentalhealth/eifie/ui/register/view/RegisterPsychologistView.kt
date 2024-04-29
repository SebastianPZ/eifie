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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.textfield.CodeTextField
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.register.RegisterViewModel
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.Purple

@Composable
fun RegisterPsychologist(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {

    val code = remember { mutableStateOf("") }
    val errorMessage by viewModel.codeError.collectAsStateWithLifecycle()

    val instructionText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.have_code))
        }
        withStyle(style = SpanStyle(
            color = Purple,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.code))
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.from))
        }
        withStyle(style = SpanStyle(
            color = Purple,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(stringResource(id = R.string.psychologist).lowercase())
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(stringResource(id = R.string.insert_it))
        }
    }


    return Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 45.dp, bottom = 30.dp),
                text = instructionText,
                textAlign = TextAlign.Center
            )
            CodeTextField(
                value = code.value,
                length = 5,
                onValueChange = {
                    code.value = it
                    if(it.length >= 5) viewModel.validateCode(it) { psychologist ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "ps",
                            value = psychologist
                        )
                        navController.navigate(Router.REGISTER_PSYCHOLOGIST_DETAIL.route)
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.updateStep(navController, Step.AUTH)
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
            Button(
                onClick = {
                    viewModel.registerUser()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(vertical = 45.dp, horizontal = 24.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.omit),
                    modifier = Modifier
                        .padding(8.dp)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "")
            }
        }
    }
}