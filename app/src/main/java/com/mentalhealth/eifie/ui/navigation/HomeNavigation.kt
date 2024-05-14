package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mentalhealth.eifie.ui.appointment.main.AppointmentView
import com.mentalhealth.eifie.ui.chat.main.ChatView
import com.mentalhealth.eifie.ui.home.HomeView
import com.mentalhealth.eifie.ui.profile.main.Profile

@Composable
fun HomeNavigation(
    navController: NavHostController,
    mainNavController: NavHostController
) {

    NavHost(navController = navController, startDestination = Router.HOME_GRAPH.route) {
        navigation(startDestination = Router.HOME.route, route = Router.HOME_GRAPH.route) {
            composable(
                route = Router.HOME.route,
            ) {
                HomeView(navController = mainNavController)
            }

            composable(
                route = Router.CHAT.route,
            ) {
                ChatView()
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