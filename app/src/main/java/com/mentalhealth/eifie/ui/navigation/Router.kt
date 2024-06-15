package com.mentalhealth.eifie.ui.navigation

enum class Router(val route: String) {
    AUTH_GRAPH("Auth"),
    INIT("init"),
    LOGIN("login"),

    REGISTER_GRAPH("Register"),
    REGISTER("register"),
    REGISTER_CONFIGURATION("register/configuration"),
    REGISTER_PSYCHOLOGIST("register/psychologist"),
    REGISTER_SUCCESS("register/success"),

    MAIN_GRAPH("Main"),
    MAIN_HOME("main/home"),
    MAIN_PROFILE("main/profile"),
    PROFILE_EDIT_PHOTO("main/profile/edit-photo"),
    SUPPORT_EDIT_PHOTO("main/support/edit-photo"),
    PSYCHOLOGIST_CODE("main/profile/psychologist-code"),
    PSYCHOLOGIST_DETAIL("main/profile/psychologist-detail"),
    PSYCHOLOGIST_UPDATE("main/profile/psychologist-update"),

    HOME_GRAPH("Home"),
    HOME("home"),
    CHAT("chat"),
    PATIENTS("patients"),
    PATIENT_DETAIL("patients/"),
    CHAT_BOX("chat/chat-box/"),
    CHAT_SUPPORT("chat/support/"),
    FORM("form/"),
    APPOINTMENT("appointment"),
    APPOINTMENT_REGISTER("appointment/register"),
    APPOINTMENT_SEARCH_PATIENT("appointment/register/search-patient"),
    PROFILE("profile")
}