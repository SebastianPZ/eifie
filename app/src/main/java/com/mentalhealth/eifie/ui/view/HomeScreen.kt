package com.mentalhealth.eifie.ui.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.form.list.NotificationListView
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.ui.view.appointment.AppointmentsState
import com.mentalhealth.eifie.ui.view.appointment.notification.AppointmentHorizontalListView
import com.mentalhealth.eifie.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController?,
    viewModel: HomeViewModel?
) {
    val user = viewModel?.userName?.collectAsStateWithLifecycle()
    val notifications = viewModel?.notifications?.collectAsStateWithLifecycle()
    val appointments = viewModel?.appointments?.collectAsStateWithLifecycle()
    val appointmentsState = viewModel?.appointmentsState?.collectAsStateWithLifecycle()

    val helloText = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = BlackGreen,
            fontWeight = FontWeight.Normal
        )
        ) {
            append("Hola, ")
        }
        withStyle(style = SpanStyle(
            color = BlackGreen,
            fontWeight = FontWeight.Bold
        )
        ) {
            append(user?.value ?: "")
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel?.getAppointmentNotifications(true)
        } else {
            // permission denied, but should I show a rationale?
        }
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel?.getAppointmentNotifications(true)
        } else {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.iv_eifi_logo),
            contentDescription = "MoonMinder",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(25.dp)
                .padding(vertical = 30.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = helloText,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = "¿Cómo te encuentras hoy?",
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 3.dp)
            )
            if(!notifications?.value.isNullOrEmpty()) {
                Text(
                    text = "Notificaciones",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )
                NotificationListView(
                    notifications = notifications?.value ?: listOf(),
                    onItemClick = {
                        navController?.navigate("${it.action ?: ""}1")
                    },
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
            Text(
                text = "Próximas citas",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            when(appointmentsState?.value) {
                AppointmentsState.Loading -> {
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
                else -> {
                    AppointmentHorizontalListView(
                        appointments = appointments?.value ?: listOf(),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun HomePreView(){
    HomeScreen(null, null)
}