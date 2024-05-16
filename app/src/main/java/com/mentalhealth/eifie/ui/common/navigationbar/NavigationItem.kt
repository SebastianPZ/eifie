package com.mentalhealth.eifie.ui.common.navigationbar

import com.mentalhealth.eifie.R
import com.mentalhealth.eifie.ui.navigation.Router

enum class NavigationItem(val label: String, val icon: Int, val route: String) {
    HOME("Inicio", R.drawable.ic_survey, Router.HOME.route),
    CHAT("Chat", R.drawable.ic_chat, Router.CHAT.route),
    APPOINTMENT("Citas", R.drawable.ic_calendar, Router.APPOINTMENT.route),
    PROFILE("Perfil", R.drawable.ic_profile, Router.PROFILE.route)
}