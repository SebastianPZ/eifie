package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mentalhealth.eifie.ui.view.appointment.main.AppointmentView
import com.mentalhealth.eifie.ui.view.HomeScreen
import com.mentalhealth.eifie.ui.profile.main.Profile
import com.mentalhealth.eifie.ui.view.chat.ChatParkScreen
import com.mentalhealth.eifie.ui.view.patient.PatientParkView
import com.mentalhealth.eifie.ui.viewmodel.ChatParkViewModel
import com.mentalhealth.eifie.ui.viewmodel.HomeViewModel
import com.mentalhealth.eifie.ui.viewmodel.PatientsViewModel

@Composable
fun HomeNavigation(
    navController: NavHostController,
    mainNavController: NavHostController?
) {

    NavHost(navController = navController, startDestination = Router.HOME_GRAPH.route) {
        navigation(startDestination = Router.HOME.route, route = Router.HOME_GRAPH.route) {
            composable(
                route = Router.HOME.route,
            ) {
                HomeScreen(
                    navController = mainNavController,
                    viewModel = hiltViewModel<HomeViewModel>()
                )
            }

            composable(
                route = Router.CHAT.route,
            ) {
                ChatParkScreen(navController = mainNavController, viewModel = hiltViewModel<ChatParkViewModel>())
            }

            composable(
                route = Router.PATIENTS.route,
            ) {
                PatientParkView(navController = mainNavController, viewModel = hiltViewModel<PatientsViewModel>())
            }

            composable(
                route = Router.APPOINTMENT.route,
            ) {
                AppointmentView(navController = mainNavController)
            }

            composable(
                route = Router.PROFILE.route,
            ) {
                Profile(mainNavController = mainNavController)
            }
        }
    }

}