package com.mentalhealth.eifie.ui.view.patient

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.ViewState
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.profile.detail.FieldOption
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.ui.theme.LightSkyGray
import com.mentalhealth.eifie.ui.theme.White90
import com.mentalhealth.eifie.ui.viewmodel.PatientDetailViewModel
import com.mentalhealth.eifie.util.getUserName

@Composable
fun PatientDetailView(
    navController: NavHostController?,
    viewModel: PatientDetailViewModel?
) {

    val state = viewModel?.state?.collectAsStateWithLifecycle()
    val patient = viewModel?.patient?.collectAsStateWithLifecycle()
    val patientInfo = viewModel?.patientInfo?.collectAsStateWithLifecycle()

    Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.eifi_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = {
                navController?.run {
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 15.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = CustomWhite,
            ),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            modifier = Modifier
                .padding(top = 120.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column (
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 35.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 80.dp)
                ) {
                    Text(
                        text = patient?.value?.lastname ?: "Prueba",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = patient?.value?.firstname ?: "Prueba",
                        fontSize = 16.sp
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(35.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier
                            .height(150.dp)
                            .padding(vertical = 20.dp)
                    ) {
                        items(patientInfo?.value ?: listOf()) {
                            Column(
                                modifier = Modifier.wrapContentHeight()
                            ) {
                                Text(text = it.first, color = LightGray, fontSize = 12.sp)
                                Text(text = it.second, fontSize = 14.sp)
                            }
                        }
                    }
                    LazyColumn {
                        itemsIndexed(viewModel?.options ?: listOf()) { index, item ->
                            HorizontalDivider(
                                color = LightSkyGray,
                                modifier = Modifier
                                    .padding(vertical = 25.dp)
                                    .fillMaxWidth()
                                    .width(1.dp)
                            )
                            FieldOption(
                                icon = item.icon,
                                label = item.label,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
        UserPhotoView(
            uri = patient?.value?.picture,
            username = patient?.value?.run { getUserName(firstname, lastname) } ?: "",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .size(140.dp)
        )
        when(state?.value) {
            is ViewState.Loading -> {
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
            else -> Unit
        }
    }
}

@Preview
@Composable
fun PsychologistDetailPreview(
) {
    PatientDetailView(
        navController = null,
        viewModel = null
    )
}