package com.mentalhealth.eifie.ui.common.navigationbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.mentalhealth.eifie.ui.navigation.Router

enum class NavigationItem(val label: String, val icon: ImageVector, val route: String) {
    HOME("Inicio", Icons.Filled.Home, Router.HOME.route),
    CHAT("Inicio", Icons.Filled.Home, "home"),
    APPOINTMENT("Citas", Icons.Filled.Home, Router.APPOINTMENT.route),
    PROFILE("Perfil", Icons.Filled.AccountCircle, Router.PROFILE.route)
}