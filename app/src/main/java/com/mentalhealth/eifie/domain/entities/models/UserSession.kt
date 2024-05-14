package com.mentalhealth.eifie.domain.entities.models

import android.os.Parcelable
import com.mentalhealth.eifie.domain.entities.states.PatientState
import com.mentalhealth.eifie.util.emptyString
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
data class UserSession(
    val uid: Int = 0,
    val profileId: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val userName: String = "",
    val birthDate: String = "",
    val hospitalId: Int = 0,
    val hospitalName: String = "",
    val role: Role = Role.PATIENT,
    var picture: String? = null,
    val status: PatientState = PatientState.NONE
): Parcelable {
    @IgnoredOnParcel
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