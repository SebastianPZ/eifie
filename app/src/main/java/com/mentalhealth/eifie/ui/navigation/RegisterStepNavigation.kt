package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.ui.register.RegisterViewModel
import com.mentalhealth.eifie.ui.register.view.RegisterPersonalData
import com.mentalhealth.eifie.ui.register.view.RegisterRole
import com.mentalhealth.eifie.ui.register.view.RegisterUserData
import com.mentalhealth.eifie.ui.register.view.RegisterPsychologist
import com.mentalhealth.eifie.ui.register.view.RegisterPsychologistDetail

@Composable
fun RegisterStepNavigation(
    viewModel: RegisterViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.REGISTER_STEP_GRAPH.route) {
        navigation(startDestination = Router.REGISTER_ROLE.route, route = Router.REGISTER_STEP_GRAPH.route) {
            composable(
                route = Router.REGISTER_ROLE.route,
            ) {
                RegisterRole(navController = navController, viewModel = viewModel)
            }

            composable(
                route = Router.REGISTER_PERSONAL.route,
            ) {
                RegisterPersonalData(navController = navController, viewModel = viewModel)
            }

            composable(
                route = Router.REGISTER_USER.route,
            ) {
                RegisterUserData(navController = navController, viewModel = viewModel)
            }

            composable(
                route = Router.REGISTER_PSYCHOLOGIST.route,
            ) {
                RegisterPsychologist(navController = navController, viewModel = viewModel)
            }

            composable(
                route = Router.REGISTER_PSYCHOLOGIST_DETAIL.route,
            ) {
                val psychologist = navController.previousBackStackEntry?.savedStateHandle?.get<Psychologist>("ps")
                RegisterPsychologistDetail(navController = navController, viewModel = viewModel, psychologist = psychologist ?: Psychologist(0, "", "", ""))
            }
        }
    }

}