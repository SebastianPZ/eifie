package com.mentalhealth.eifie.ui.register.email

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.common.button.CancelButtonView
import com.mentalhealth.eifie.ui.common.textfield.CodeTextField
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.Purple

@Composable
fun ValidateEmailComponent(
    onBack: () -> Unit,
    onContinue: (String) -> Unit
) {

    val code = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val instructionText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append("Hemos enviado un ")
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
            append("verificación")
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append(" a su ")
        }
        withStyle(style = SpanStyle(
            color = Purple,
            fontWeight = FontWeight.Bold
        )
        ) {
            append("correo.")
        }
        withStyle(style = SpanStyle(
            fontWeight = FontWeight.Normal
        )
        ) {
            append("\nPor favor, ingréselo a continuación.")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomWhite)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 45.dp, horizontal = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
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
                            keyboardController?.hide()
                        }
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                Text(
                    text = "",
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
                CancelButtonView {
                    onBack()
                }
                AcceptButtonView(
                    text = stringResource(id = R.string.register_button),
                    enabled = code.value.length >= 5
                ) {
                    onContinue(code.value)
                }
            }
        }

    }
}

@Preview
@Composable
fun ValidateEmailPreview() {
    ValidateEmailComponent({}, {})
}