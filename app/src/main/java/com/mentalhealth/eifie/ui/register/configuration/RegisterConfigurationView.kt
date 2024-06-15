package com.mentalhealth.eifie.ui.register.configuration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.button.AcceptButtonView
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.profile.detail.FieldOption
import com.mentalhealth.eifie.ui.theme.BlackGreen
import com.mentalhealth.eifie.ui.theme.LightSkyGray

@Composable
fun RegisterConfigurationView(
    role: Int,
    user: Long,
    navController: NavHostController
) {

    val viewModel: RegisterConfigurationViewModel = hiltViewModel<RegisterConfigurationViewModel, RegisterConfigurationViewModel.RegisterConfigurationViewModelFactory>(
        creationCallback = { factory -> factory.create(role = role) })

    val options by viewModel.options.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 37.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painter = painterResource(id = R.drawable.iv_eifi_logo),
                contentDescription = "MoodMinder",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(25.dp)
                    .padding(vertical = 30.dp)
            )
            Text(
                text = stringResource(id = R.string.register_configuration_title),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .wrapContentSize()
            ) {
                itemsIndexed(options) { index, item ->
                    FieldOption(
                        icon = item.icon,
                        label = item.label,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "patient",
                                value = user
                            )
                            navController.navigate(item.value)
                        }
                    )
                    if(index < options.lastIndex) {
                        HorizontalDivider(
                            color = LightSkyGray,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 15.dp)
                                .fillMaxWidth()
                                .width(1.dp)
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .padding(vertical = 45.dp)
                .fillMaxWidth()
        ) {
            AcceptButtonView(
                text = stringResource(id = R.string.omit)
            ) {
                navController.navigate(Router.REGISTER_SUCCESS.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = false
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }
        }
    }
}