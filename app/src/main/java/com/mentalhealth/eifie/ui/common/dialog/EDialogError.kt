package com.mentalhealth.eifie.ui.common.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.White90

@Composable
fun EDialogError(
    title: String,
    message: String,
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = White90
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                EAnimation(
                    resource = R.raw.error_animation,
                    animationModifier = Modifier
                        .size(120.dp)
                        .padding(top = 20.dp, bottom = 10.dp)
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = message,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 16.dp)
                )
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlackGreen
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.retry),
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun EDialogError(
    title: String,
    message: String,
    onSuccessEnabled: Boolean = true,
    onSuccessText: String = stringResource(id = R.string.retry),
    onCancelText: String  = "",
    onSuccess: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = White90
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                EAnimation(
                    resource = R.raw.error_animation,
                    animationModifier = Modifier
                        .size(120.dp)
                        .padding(top = 20.dp, bottom = 10.dp)
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = message,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 16.dp)
                )
                if(onSuccessEnabled) {
                    OutlinedButton(
                        onClick = {
                            onSuccess.invoke()
                            onDismissRequest.invoke()
                        },
                        border = BorderStroke(
                            width = 1.dp,
                            color = BlackGreen
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 5.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = onSuccessText,
                            color = BlackGreen,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        onCancel.invoke()
                        onDismissRequest.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlackGreen
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = onCancelText,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}