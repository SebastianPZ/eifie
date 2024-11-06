package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mentalhealth.eifie.ui.init.InitScreen
import com.mentalhealth.eifie.ui.login.LoginView
import com.mentalhealth.eifie.ui.login.LoginViewModel
import com.mentalhealth.eifie.ui.login.RecoverPasswordView
import com.mentalhealth.eifie.ui.login.RecoverPasswordViewModel

@Composable
fun AuthNavigation(
    navigateToHome: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.AUTH_GRAPH.route) {
        navigation(startDestination = Router.INIT.route, route = Router.AUTH_GRAPH.route) {
            composable(
                route = Router.INIT.route,
            ) {
                InitScreen(navController)
            }
            composable(
                route = Router.LOGIN.route,
            ) {
                LoginView(
                    navController = navController,
                    viewModel = hiltViewModel<LoginViewModel>(),
                    navigateToHome = navigateToHome,
                )
            }
            composable(
                route = Router.RECOVER_PASSWORD.route,
            ) {
                RecoverPasswordView(
                    navController = navController,
                    viewModel = hiltViewModel<RecoverPasswordViewModel>()
                )
            }
        }
    }

}