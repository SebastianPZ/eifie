package com.mentalhealth.eifie.ui.init

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.RegisterActivity
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.DarkGreen

@Composable
fun InitScreen(
    navController: NavHostController?
) {

    val context = LocalContext.current

    return Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .paint(
                painter = painterResource(id = R.drawable.eifi_background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.iv_eifi_logotype),
                contentDescription = "MoodMinder",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp)
            )
            Button(
                onClick = {
                          navController?.navigate(Router.LOGIN.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BlackGreen
                ),
                modifier = Modifier
                    .padding(top = 75.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            Button(
                onClick = {
                    context.startActivity(Intent(context, RegisterActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkGreen
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 25.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.register_button),
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    InitScreen(null)
}