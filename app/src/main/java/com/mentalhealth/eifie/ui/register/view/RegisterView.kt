package com.mentalhealth.eifie.ui.register.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.navigation.RegisterStepNavigation
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.register.RegisterViewModel
import com.mentalhealth.eifie.ui.register.RegisterViewState
import com.mentalhealth.eifie.ui.theme.White95
import com.mentalhealth.eifie.ui.theme.cutiveFontFamily
import com.mentalhealth.eifie.util.ERR_REGISTER
import com.mentalhealth.eifie.util.getActivity

@Composable
fun Register(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {

    val context = LocalContext.current.getActivity()

    viewModel.registerNavigateUp { context?.finish() }

    return Box {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.eifi_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            RegisterHeader(viewModel = viewModel)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = White95,
                ),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                RegisterStepNavigation(viewModel = viewModel)
            }
        }
        Loading(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun RegisterHeader(viewModel: RegisterViewModel) {

    val step by  viewModel.step.collectAsStateWithLifecycle()

    return with(step) {
        Text(
            modifier = Modifier.padding(start = 24.dp, top = 45.dp),
            text = if(required) stringResource(id = R.string.step, ordinal, viewModel.stepsSize)
                else stringResource(id = R.string.optional_step),
            fontSize = 12.sp,
            fontFamily = cutiveFontFamily
        )
        Text(
            modifier = Modifier.padding(start = 24.dp, top = 2.dp),
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Loading(
    viewModel: RegisterViewModel,
    navController: NavHostController
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    when(state) {
        RegisterViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            )
        }
        is RegisterViewState.Success -> {
            EAnimation(
                resource = R.raw.success_animation,
                iterations = 1,
                actionOnEnd = {
                    navController.navigate(Router.REGISTER_SUCCESS.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = false
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            )
        }
        is RegisterViewState.Error -> (state as RegisterViewState.Error).run {
            EDialogError(
                title = ERR_REGISTER,
                message = message,
                onCancelText = stringResource(id = R.string.go_back_login),
                onCancel = { viewModel.onBackPressed.invoke() },
                onDismissRequest = {
                    viewModel.setViewInitialStatus()
                }
            )
        }
        else -> Unit
    }


}