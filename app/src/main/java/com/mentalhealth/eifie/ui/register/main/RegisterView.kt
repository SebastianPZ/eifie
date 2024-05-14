package com.mentalhealth.eifie.ui.register.main

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.domain.entities.models.Role
import com.mentalhealth.eifie.ui.common.animation.EAnimation
import com.mentalhealth.eifie.ui.common.dialog.EDialogError
import com.mentalhealth.eifie.ui.common.layout.DefaultViewLayout
import com.mentalhealth.eifie.ui.navigation.Router
import com.mentalhealth.eifie.ui.register.RegisterViewState
import com.mentalhealth.eifie.ui.register.Step
import com.mentalhealth.eifie.ui.register.role.RegisterRoleComponent
import com.mentalhealth.eifie.ui.theme.CustomWhite
import com.mentalhealth.eifie.util.ERR_REGISTER
import com.mentalhealth.eifie.util.getActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterView(
    navController: NavHostController,
    context: Context = LocalContext.current,
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>(viewModelStoreOwner = context.getActivity()!!)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val step by viewModel.actualStep.collectAsStateWithLifecycle()
    val role by viewModel.role.collectAsStateWithLifecycle()
    val roles by viewModel.roles.collectAsStateWithLifecycle()
    val hospitals by viewModel.hospitals.collectAsStateWithLifecycle()

    val personalData by viewModel.personalData.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState( pageCount = { viewModel.stepsSize })

    DefaultViewLayout(
        header = {
            RegisterHeaderComponent(
                step = step.order + 1,
                stepsSize = viewModel.stepsSize,
                title = step.title
            )
        }
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {step ->
            when(step) {
                Step.FIRST -> RegisterRoleComponent(
                    roles = roles,
                    onRoleChange = { viewModel.updateSelectedRole(it) },
                    onBack = { context.getActivity()?.finish() },
                    onContinue = {
                        coroutineScope.launch {
                            viewModel.updateStep(Step.SECOND)
                            pagerState.animateScrollToPage(Step.SECOND)
                        }
                    }
                )
                Step.SECOND -> RegisterPersonalComponent(
                    role = role,
                    personalData = personalData,
                    hospitals = hospitals,
                    initHospitals = { viewModel.initHospitals() },
                    onBack = {
                        coroutineScope.launch {
                            viewModel.updateStep(Step.FIRST)
                            pagerState.animateScrollToPage(Step.FIRST)
                        }
                    },
                    onContinue = {
                        coroutineScope.launch {
                            viewModel.setPersonalData(it)
                            viewModel.updateStep(Step.THIRD)
                            pagerState.animateScrollToPage(Step.THIRD)
                        }
                    }
                )
                Step.THIRD -> RegisterUserComponent(
                    userData = userData,
                    onBack = {
                        coroutineScope.launch {
                            viewModel.updateStep(Step.SECOND)
                            pagerState.animateScrollToPage(Step.SECOND)
                        }
                    },
                    onContinue = {
                        viewModel.setUserData(it)
                        viewModel.registerUser()
                    }
                )
            }
        }
    }

    when(state) {
        RegisterViewState.Loading -> {
            EAnimation(
                resource = R.raw.loading_animation,
                animationModifier = Modifier
                    .size(150.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = CustomWhite)
            )
        }
        is RegisterViewState.Success -> (state as RegisterViewState.Success ).run{
            EAnimation(
                resource = R.raw.success_animation,
                iterations = 1,
                actionOnEnd = {
                    when(role?.id) {
                        Role.PSYCHOLOGIST.ordinal -> {
                            navController.navigate(Router.REGISTER_SUCCESS.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                        else -> {
                            navController.currentBackStackEntry?.savedStateHandle?.apply {
                                set(key = "role", value = role?.id)
                                set(key = "user", value = user)
                            }

                            navController.navigate(Router.REGISTER_CONFIGURATION.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    }
                },
                animationModifier = Modifier
                    .size(250.dp),
                backgroundModifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = CustomWhite)
            )
        }
        is RegisterViewState.Error -> (state as RegisterViewState.Error).run {
            EDialogError(
                title = ERR_REGISTER,
                message = message,
                onCancelText = stringResource(id = R.string.go_back_login),
                onCancel = { context.getActivity()?.finish() },
                onDismissRequest = {
                    viewModel.setViewInitialStatus()
                }
            )
        }
        else -> Unit
    }
}