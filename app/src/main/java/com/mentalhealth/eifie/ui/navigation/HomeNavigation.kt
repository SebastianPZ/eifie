package com.mentalhealth.eifie.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.mentalhealth.eifie.ui.appointment.Appointment
import com.mentalhealth.eifie.ui.home.Home
import com.mentalhealth.eifie.ui.profile.Profile

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
                Home()
            }

            composable(
                route = Router.APPOINTMENT.route,
            ) {
                Appointment()
            }

            composable(
                route = Router.PROFILE.route,
            ) {
                Profile(mainNavController = mainNavController)
            }
        }
    }

}