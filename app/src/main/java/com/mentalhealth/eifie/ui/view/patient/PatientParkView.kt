package com.mentalhealth.eifie.ui.view.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.DarkGreen
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.ui.view.chat.ChatParkHeader
import com.mentalhealth.eifie.ui.viewmodel.PatientsViewModel

@Composable
fun PatientParkView(
    navController: NavHostController?,
    viewModel: PatientsViewModel?
) {

    val patients = viewModel?.patients?.collectAsStateWithLifecycle()
    val state = viewModel?.state?.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = CustomWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            ChatParkHeader(
                title = stringResource(id = R.string.patients),
                options = {
                    SearchButton { }
                    RefreshButton { viewModel?.refreshPatients() }
                },
                modifier = Modifier.padding(top = 25.dp, bottom = 20.dp)
            )
            when {
                state?.value is ViewState.Loading -> {
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
                patients?.value?.isEmpty() != false -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.empty_patients),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(patients.value.size) { index ->
                            PatientItem(
                                patient = patients.value[index],
                                onClick = {
                                    navController?.navigate("${Router.PATIENT_DETAIL.route}${it.id}")
                                }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SearchButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = DarkGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
private fun RefreshButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            tint = DarkGreen,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview
@Composable
fun PatientListPreview() {
    PatientParkView(null, null)
}