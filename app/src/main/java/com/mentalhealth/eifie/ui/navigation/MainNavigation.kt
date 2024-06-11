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
import com.mentalhealth.eifie.ui.view.chatbox.ChatScreen
import com.mentalhealth.eifie.ui.form.main.SurveyView
import com.mentalhealth.eifie.ui.form.main.SurveyViewModel
import com.mentalhealth.eifie.ui.profile.detail.ProfileDetail
import com.mentalhealth.eifie.ui.profile.detail.ProfileDetailViewModel
import com.mentalhealth.eifie.ui.profile.edit.EditProfilePhoto
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailView
import com.mentalhealth.eifie.ui.psychologist.PsychologistDetailViewModel
import com.mentalhealth.eifie.ui.psychologist.accesscode.PsychologistCodeView
import com.mentalhealth.eifie.ui.register.psychologist.RegisterPsychologistView
import com.mentalhealth.eifie.ui.view.appointment.register.AppointmentRegisterViewModel
import com.mentalhealth.eifie.ui.view.appointment.register.SearchPatientView
import com.mentalhealth.eifie.ui.view.patient.PatientDetailView
import com.mentalhealth.eifie.ui.view.support.SupportSettingsView
import com.mentalhealth.eifie.ui.viewmodel.ParkViewModel
import com.mentalhealth.eifie.ui.viewmodel.ChatViewModel
import com.mentalhealth.eifie.ui.viewmodel.PatientDetailViewModel
import com.mentalhealth.eifie.ui.viewmodel.SearchPatientViewModel
import com.mentalhealth.eifie.ui.viewmodel.SupportSettingsViewModel

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
                SurveyView(
                    navController = navController,
                    viewModel = hiltViewModel<SurveyViewModel, SurveyViewModel.FormViewModelFactory>(
                        creationCallback = { factory -> factory.create(id = arguments.getInt("form")) })
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
                route = Router.CHAT_SUPPORT.route
            ) {
                SupportSettingsView(
                    navController = navController,
                    viewModel = hiltViewModel<SupportSettingsViewModel>()
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
                val name = it.savedStateHandle.getLiveData<String>("patientName")
                val patient = it.savedStateHandle.get<Long>("patientId").let { id ->
                    (id ?: -1) to name
                }
                AppointmentRegisterView(navController = navController, viewModel = hiltViewModel<AppointmentRegisterViewModel>(), patient = patient)
            }

            composable(
                route = Router.APPOINTMENT_SEARCH_PATIENT.route,
            ) {
                SearchPatientView(navController = navController, viewModel = hiltViewModel<SearchPatientViewModel>())
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
                val patient = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("user") ?: 1
                val psychologist = it.savedStateHandle.get<Long>("psychologist") ?: arguments.getLong("psychologist")
                PsychologistDetailView(
                    psychologistId = psychologist,
                    patientId = patient,
                    navController = navController,
                    viewModel = hiltViewModel<PsychologistDetailViewModel>()
                )
            }

            composable(
                route = Router.PSYCHOLOGIST_UPDATE.route,
            ) {
                val patient = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("user") ?: 1
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