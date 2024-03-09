package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.login.Login

@Composable
fun AuthNavigation(
    navigateToHome: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.AUTH_GRAPH.route) {
        navigation(startDestination = Router.LOGIN.route, route = Router.AUTH_GRAPH.route) {
            composable(
                route = Router.LOGIN.route,
            ) {
                Login(navigateToHome = navigateToHome)
            }
        }
    }

}