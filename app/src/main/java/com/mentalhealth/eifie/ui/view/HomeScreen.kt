package com.mentalhealth.eifie.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.Role
import com.mentalhealth.eifie.ui.form.list.NotificationListView
import com.mentalhealth.eifie.ui.profile.detail.FieldOption
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController?,
    viewModel: HomeViewModel?
) {
    val user = viewModel?.userName?.collectAsStateWithLifecycle()
    val notifications = viewModel?.notifications?.collectAsStateWithLifecycle()

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

    }
}

@Preview
@Composable
fun HomePreView(){
    HomeScreen(null, null)
}