package com.mentalhealth.eifie.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.mentalhealth.eifie.domain.entities.models.Form
import com.mentalhealth.eifie.domain.entities.models.Patient
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.form.list.FormListView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.patient.PatientListView
import com.mentalhealth.eifie.ui.theme.BlackGreen

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {

    val role by viewModel.userRole.collectAsStateWithLifecycle()
    val user by viewModel.userName.collectAsStateWithLifecycle()
    val formList by viewModel.formList.collectAsStateWithLifecycle()


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
            append(user)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
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
                text = if(role == Role.PATIENT) "Actividades" else "Pacientes",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 30.dp)
            )
        }
        if(role == Role.PATIENT) {
            FormListView(
                formList = formList,
                onItemClick = { form -> navController.navigate("${Router.FORM.route}${form.id}") },
                modifier = Modifier.padding(top = 34.dp)
            )
        } else {
            PatientListView(
                patientList = listOf(
                    Patient(0, "Luis Lapiedra", "10 semanas", "En Progreso"),
                    Patient(0, "Luis Lapiedra", "10 semanas", "En Progreso")
                ),
                modifier = Modifier.padding(top = 34.dp)
            )
        }
    }
}

@Preview
@Composable
fun HomePreView(){
    HomeView(rememberNavController())
}