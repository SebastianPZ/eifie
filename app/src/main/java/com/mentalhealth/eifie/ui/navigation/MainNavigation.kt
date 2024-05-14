package com.mentalhealth.eifie.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mentalhealth.eifie.domain.entities.models.Psychologist
import com.mentalhealth.eifie.domain.entities.models.UserSession
import com.mentalhealth.eifie.ui.appointment.register.AppointmentRegisterView
import com.mentalhealth.eifie.ui.base.BaseView
import com.mentalhealth.eifie.ui.form.main.FormView
import com.mentalhealth.eifie.ui.profile.detail.ProfileDetail
import com.mentalhealth.eifie.ui.profile.edit.EditProfilePhoto
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailView
import com.mentalhealth.eifie.ui.psychologist.accesscode.PsychologistCodeView
import com.mentalhealth.eifie.ui.register.psychologist.RegisterPsychologistView

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.MAIN_GRAPH.route) {
        navigation(startDestination = Router.MAIN_HOME.route, route = Router.MAIN_GRAPH.route) {
            composable(
                route = Router.MAIN_HOME.route,
            ) {
                BaseView(mainNavController = navController)
            }

            composable(
                route = "${Router.FORM.route}{form}",
                arguments = listOf(navArgument("form") { type = NavType.IntType })
            ) {
                val arguments = requireNotNull(it.arguments)
                FormView(
                    id = arguments.getInt("form"),
                    navController = navController
                )
            }

            composable(
                route = Router.APPOINTMENT_REGISTER.route,
            ) {
                AppointmentRegisterView(navController = navController)
            }

            composable(
                route = Router.MAIN_PROFILE.route,
            ) {
                ProfileDetail(navController = navController)
            }

            composable(
                route = Router.PSYCHOLOGIST_CODE.route,
            ) {
                val psychologist = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("userId") ?: 1
                PsychologistCodeView(psychologist = psychologist, navController = navController)
            }

            composable(
                route = Router.PSYCHOLOGIST_DETAIL.route,
            ) {
                PsychologistDetailView(psychologist = -1, navController = navController)
            }

            composable(
                route = Router.PSYCHOLOGIST_UPDATE.route,
            ) {
                val patient = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("userId") ?: 1
                RegisterPsychologistView(patient = patient, navController = navController)
            }

            composable(
                route = Router.PROFILE_EDIT_PHOTO.route,
            ) {
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<UserSession>("user") ?: UserSession()
                EditProfilePhoto(user = user, navController = navController)
            }
        }
    }

}