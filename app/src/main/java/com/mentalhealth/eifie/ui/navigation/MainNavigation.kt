package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.main.MainHome
import com.mentalhealth.eifie.ui.profile.ProfileDetail

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.MAIN_GRAPH.route) {
        navigation(startDestination = Router.MAIN_HOME.route, route = Router.MAIN_GRAPH.route) {
            composable(
                route = Router.MAIN_HOME.route,
            ) {
                MainHome(mainNavController = navController)
            }

            composable(
                route = Router.MAIN_PROFILE.route,
            ) {
                ProfileDetail(navController = navController)
            }
        }
    }

}