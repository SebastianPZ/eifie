package com.mentalhealth.eifie.ui.register

import com.mentalhealth.eifie.ui.navigation.Router

enum class Step(val title: String, val route: Router) {
    AUTH("Auth", Router.AUTH_GRAPH),
    ROLE_DATA("Rol", Router.REGISTER_ROLE),
    PERSONAL_DATA("Datos", Router.REGISTER_PERSONAL),
    USER_DATA("Usuario", Router.REGISTER_USER)
}