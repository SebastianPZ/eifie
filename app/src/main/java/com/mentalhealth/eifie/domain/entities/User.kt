package com.mentalhealth.eifie.domain.entities

import android.os.Parcelable
import com.mentalhealth.eifie.util.calculateAge
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: Long = 0,
    val profileId: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val userName: String = "",
    val birthDate: String = "",
    val hospitalId: Int = 0,
    val hospitalName: String = "",
    val role: Role = Role.PATIENT,
    var picture: String? = null,
    var psychologistAssigned: Long = -1,
    val status: PatientState = PatientState.NONE
): Parcelable {
    @IgnoredOnParcel
    val age = calculateAge(birthDate)
}