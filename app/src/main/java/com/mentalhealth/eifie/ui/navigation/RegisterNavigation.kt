package com.mentalhealth.eifie.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.ui.register.configuration.RegisterConfigurationView
import com.mentalhealth.eifie.ui.register.main.RegisterViewModel
import com.mentalhealth.eifie.ui.register.main.RegisterView
import com.mentalhealth.eifie.ui.register.main.RegisterSuccess
import com.mentalhealth.eifie.ui.register.psychologist.RegisterPsychologistView

@Composable
fun RegisterNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.REGISTER_GRAPH.route) {
        navigation(startDestination = Router.REGISTER.route, route = Router.REGISTER_GRAPH.route) {
            composable(
                route = Router.REGISTER.route,
            ) {
                //RegisterConfigurationView(role = 0, user = 0, navController = navController)
                RegisterView(navController = navController)
            }

            composable(
                route = Router.REGISTER_CONFIGURATION.route,
            ) {
                val role = navController.previousBackStackEntry?.savedStateHandle?.get<Int>("role") ?: 1
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("user") ?: 1
                RegisterConfigurationView(role = role, user = user, navController = navController)
            }

            composable(
                route = Router.REGISTER_PSYCHOLOGIST.route,
            ) {
                val patient = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("patient") ?: 1
                RegisterPsychologistView(patient = patient, navController = navController)
            }

            composable(
                route = Router.REGISTER_SUCCESS.route,
            ) {
                RegisterSuccess()
            }
        }
    }

}