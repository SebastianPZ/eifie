package com.mentalhealth.eifie.ui.navigation

enum class Router(val route: String) {
    AUTH_GRAPH("Auth"),
    LOGIN("login"),

    REGISTER_GRAPH("Register"),
    REGISTER("register"),
    REGISTER_SUCCESS("register/success"),
    REGISTER_ERROR("register/error"),

    REGISTER_STEP_GRAPH("RegisterStep"),
    REGISTER_ROLE("register/role"),
    REGISTER_PERSONAL("register/personal"),
    REGISTER_USER("register/user"),

    MAIN_GRAPH("Main"),
    MAIN_HOME("main/home"),
    MAIN_PROFILE("main/profile"),

    HOME_GRAPH("Home"),
    HOME("home"),
    APPOINTMENT("appointment"),
    PROFILE("profile")
}