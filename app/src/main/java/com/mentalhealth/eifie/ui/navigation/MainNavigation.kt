package com.mentalhealth.eifie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mentalhealth.eifie.domain.entities.User
import com.mentalhealth.eifie.ui.view.appointment.register.AppointmentRegisterView
import com.mentalhealth.eifie.ui.view.ParkScreen
import com.mentalhealth.eifie.ui.view.chat.chatbox.ChatScreen
import com.mentalhealth.eifie.ui.form.main.FormView
import com.mentalhealth.eifie.ui.profile.detail.ProfileDetail
import com.mentalhealth.eifie.ui.profile.detail.ProfileDetailViewModel
import com.mentalhealth.eifie.ui.profile.edit.EditProfilePhoto
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailView
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailViewModel
import com.mentalhealth.eifie.ui.psychologist.accesscode.PsychologistCodeView
import com.mentalhealth.eifie.ui.register.configuration.RegisterConfigurationViewModel
import com.mentalhealth.eifie.ui.register.psychologist.RegisterPsychologistView
import com.mentalhealth.eifie.ui.view.patient.PatientDetailView
import com.mentalhealth.eifie.ui.viewmodel.ParkViewModel
import com.mentalhealth.eifie.ui.viewmodel.ChatViewModel
import com.mentalhealth.eifie.ui.viewmodel.PatientDetailViewModel

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Router.MAIN_GRAPH.route) {
        navigation(startDestination = Router.MAIN_HOME.route, route = Router.MAIN_GRAPH.route) {
            composable(
                route = Router.MAIN_HOME.route,
            ) {
                ParkScreen(mainNavController = navController, viewModel = hiltViewModel<ParkViewModel>())
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
                route = "${Router.CHAT_BOX.route}{chat}",
                arguments = listOf(navArgument("chat") { type = NavType.LongType })
            ) {
                val arguments = requireNotNull(it.arguments)
                ChatScreen(
                    navController = navController,
                    viewModel = hiltViewModel<ChatViewModel, ChatViewModel.ChatBoxViewModelFactory> (
                        creationCallback = { factory -> factory.create(chatId = arguments.getLong("chat")) })
                )
            }

            composable(
                route = "${Router.PATIENT_DETAIL.route}{patient}",
                arguments = listOf(navArgument("patient") { type = NavType.LongType })
            ) {
                val arguments = requireNotNull(it.arguments)
                PatientDetailView(
                    navController = navController,
                    viewModel = hiltViewModel<PatientDetailViewModel, PatientDetailViewModel.PatientDetailViewModelFactory>(
                        creationCallback = { factory -> factory.create(patientId = arguments.getLong("patient")) })
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
                ProfileDetail(navController = navController, viewModel =  hiltViewModel<ProfileDetailViewModel>())
            }

            composable(
                route = Router.PSYCHOLOGIST_CODE.route,
            ) {
                val psychologist = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("userId") ?: 1
                PsychologistCodeView(psychologist = psychologist, navController = navController)
            }

            composable(
                route = "${Router.PSYCHOLOGIST_DETAIL.route}{psychologist}",
                arguments = listOf(navArgument("psychologist") { type = NavType.LongType })
            ) {
                val arguments = requireNotNull(it.arguments)
                PsychologistDetailView(
                    psychologistId = arguments.getLong("psychologist"),
                    navController = navController,
                    viewModel = hiltViewModel<PsychologistDetailViewModel, PsychologistDetailViewModel.PsychologistDetailViewModelFactory>(
                        creationCallback = { factory -> factory.create(psychologistId = arguments.getLong("psychologist")) })
                )
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
                val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user") ?: User()
                EditProfilePhoto(user = user, navController = navController)
            }
        }
    }

}