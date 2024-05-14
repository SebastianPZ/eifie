package com.mentalhealth.eifie.ui.psychologist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.mentalhealth.eifie.ui.navigation.Router

@Composable
fun PsychologistDetailView(
    psychologist: Long,
    navController: NavHostController
) {
    LaunchedEffect(Unit) {
        if(psychologist < 0) {
            navController.navigate(Router.PSYCHOLOGIST_UPDATE.route) {
                popUpTo(Router.PSYCHOLOGIST_DETAIL.route) {
                    saveState = false
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }
}