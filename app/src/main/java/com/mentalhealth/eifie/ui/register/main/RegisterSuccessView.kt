package com.mentalhealth.eifie.ui.register.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.LightGreen
import com.mentalhealth.eifie.ui.theme.Purple
import com.mentalhealth.eifie.util.getActivity

@Composable
fun RegisterSuccess(
    context: Context = LocalContext.current,
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>(viewModelStoreOwner = context.getActivity()!!)
) {

    Box(
        modifier = Modifier.background(color = CustomWhite)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mind_register),
            contentDescription = "",
            tint = LightGreen,
            modifier = Modifier
                .size(320.dp)
                .padding(top = 55.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.iv_vector_register),
            contentDescription = "",
            tint = LightGreen,
            modifier = Modifier
                .size(320.dp)
                .padding(bottom = 20.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-150).dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 80.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.welcome_title_no_space),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Text(
                    text = viewModel.user.firstName,
                    color = Purple,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Text(
                    text = stringResource(id = R.string.welcome_presentation_text),
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()

                )
            }
            Button(
                onClick = {
                    context.getActivity()?.finish()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(top = 100.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}