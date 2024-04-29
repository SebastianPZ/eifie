package com.mentalhealth.eifie.ui.register

import com.mentalhealth.eifie.ui.navigation.Router

enum class Step(val title: String, val route: Router, val required: Boolean) {
    AUTH("Auth", Router.AUTH_GRAPH, true),
    ROLE_DATA("Rol", Router.REGISTER_ROLE, true),
    PERSONAL_DATA("Datos", Router.REGISTER_PERSONAL, true),
    USER_DATA("Usuario", Router.REGISTER_USER, true),
    PSYCHOLOGIST("Psicólogo", Router.REGISTER_PSYCHOLOGIST, false),
}