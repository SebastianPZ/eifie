package com.mentalhealth.eifie.ui.psychologist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.photo.UserPhotoView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.ui.theme.LightGray
import com.mentalhealth.eifie.util.calculateAge
import com.mentalhealth.eifie.util.getUserName

@Composable
fun PsychologistDetailView(
    psychologistId: Long,
    navController: NavHostController?,
    viewModel: PsychologistDetailViewModel?
) {

    val psychologist = viewModel?.psychologist?.collectAsStateWithLifecycle()
    val psychologistInfo = viewModel?.psychologistInfo?.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if(psychologistId < 0) {
            navController?.navigate(Router.PSYCHOLOGIST_UPDATE.route) {
                popUpTo(Router.PSYCHOLOGIST_DETAIL.route) {
                    saveState = false
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }

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
                modifier = Modifier.fillMaxSize().padding(horizontal = 35.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 80.dp)
                ) {
                    Text(
                        text = psychologist?.value?.lastName ?: "Prueba",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = psychologist?.value?.firstName ?: "Prueba",
                        fontSize = 16.sp
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(45.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.height(150.dp).padding(vertical = 20.dp)
                    ) {
                        items(psychologistInfo?.value ?: listOf()) {
                            Column(
                                modifier = Modifier.wrapContentHeight()
                            ) {
                                Text(text = it.first, color = LightGray, fontSize = 12.sp)
                                Text(text = it.second, fontSize = 14.sp)
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        navController?.navigate(Router.PSYCHOLOGIST_UPDATE.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlackGreen
                    ),
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 45.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.update),
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }
        UserPhotoView(
            uri = psychologist?.value?.picture,
            username = psychologist?.value?.run { getUserName(firstName, lastName) } ?: "",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .size(140.dp)
        )
    }
}

@Preview
@Composable
fun PsychologistDetailPreview(
) {
    PsychologistDetailView(psychologistId = -1, navController = null, viewModel = null)
}