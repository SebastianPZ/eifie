package com.mentalhealth.eifie.util

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import java.util.regex.Pattern


const val ERR_LOGIN = "Error de inicio de sesión."
const val ERR_REGISTER = "Error de registro."
const val ERR_SURVEY = "Error en envío de formulario."
const val ERR_SURVEY_DONE = "Formulario completo."
const val ERR_SAVE_USER_SESSION = "Hubo un error durante el inicio de sesión."
const val TRY_AGAIN = "Por favor vuelva a intentar."

const val ERR_LENGTH = "Mínimo 4 caracteres"
const val ERR_WHITESPACE = "No espacios en blancos"
const val ERR_DIGIT = "Al menos un dígito"
const val ERR_SPECIAL = "Al menos un caracter especial"
const val ERR_EMAIL = "Ingrese un correo válido"
const val ERR_EMPTY = "Este campo no puede estar vacio"
const val ERR_SAME_TEXT = "Las contraseñas deben ser iguales"

const val ERR_ACCESS_CODE = "Error en validación de código."

const val NO_INFO = "Sin información"
const val PENDANT = "Pendiente"

const val AGE_TITLE = "Edad"
const val STATUS_TITLE = "Estado"
const val HOSPITAL_TITLE = "Hospital"
const val FIRSTNAME_TITLE = "Nombres"
const val LASTNAME_TITLE = "Apellidos"
const val BIRTHDATE_TITLE = "Fecha de nacimiento"
const val EMAIL_TITLE = "Correo"
const val PSYCHOLOGIST_CODE = "Código de psicólogo"
const val PSYCHOLOGIST_ASSIGN = "Asignación de psicólogo"


const val TOKEN_KEY = "token"
const val USER_KEY = "user"
const val USER_ROLE_KEY = "user_role"
val tokenPreferences = stringPreferencesKey(TOKEN_KEY)
val userPreferences = longPreferencesKey(USER_KEY)
val userRolePreferences = longPreferencesKey(USER_ROLE_KEY)