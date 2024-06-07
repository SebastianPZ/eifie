package com.mentalhealth.eifie.ui.register.psychologist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.textfield.CodeTextField
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.util.getActivity

@Composable
fun PsychologistCodeComponent(
    errorMessage: String,
    state: ViewState,
    validateCode: (String) -> Unit,
    onSuccess: () -> Unit = {}
) {
    val code = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

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


    Box {
        Column(
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
                    modifier = Modifier.padding(top = 8.dp, bottom = 45.dp),
                    text = instructionText,
                    textAlign = TextAlign.Center
                )
                CodeTextField(
                    value = code.value,
                    length = 5,
                    onValueChange = {
                        code.value = it
                        if(it.length >= 5) {
                            validateCode(it)
                            keyboardController?.hide()
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

            //options
        }

        when(state) {
            ViewState.Loading -> {
                EAnimation(
                    resource = R.raw.loading_animation,
                    animationModifier = Modifier
                        .size(150.dp),
                    backgroundModifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = CustomWhite)
                )
            }
            is ViewState.Success -> {
                EAnimation(
                    resource = R.raw.success_animation,
                    iterations = 1,
                    actionOnEnd = onSuccess,
                    animationModifier = Modifier
                        .size(250.dp),
                    backgroundModifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = CustomWhite)
                )
            }
            else -> Unit
        }
    }
}