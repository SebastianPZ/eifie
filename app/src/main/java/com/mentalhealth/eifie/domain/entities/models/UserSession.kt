package com.mentalhealth.eifie.domain.entities.models

import com.mentalhealth.eifie.domain.entities.states.PatientState
import com.mentalhealth.eifie.util.emptyString
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class UserSession(
    val uid: Int,
    val profileId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val userName: String,
    val birthDate: String,
    val hospitalId: Int,
    val hospitalName: String,
    val role: Role,
    var picture: String? = null,
    val status: PatientState = PatientState.NONE
) {
    val age = calculateAge(birthDate)
}

fun getUserName(firstName: String, lastName: String): String {
    var nameOne = emptyString()
    var nameTwo = emptyString()

    if(firstName.isNotEmpty()) {
        val firstNameWords = firstName.split(" ")
        nameOne = firstNameWords.first()
    }

    if(lastName.isNotEmpty()) {
        val lastNameWords = lastName.split(" ")
        nameTwo = lastNameWords.first()
    }

    return "$nameOne $nameTwo"
}

fun calculateAge(birthDate: String): Int {
    return try {
        val currentYear = LocalDate.now().year
        val birthYear = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).year
        currentYear - birthYear
    } catch (e: Exception) {
        0
    }
}