package com.mentalhealth.eifie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Psychologist(
    val id: Long = 0,
    val uId: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val hospital: String = "",
    val birthDate: String = "",
    val email: String = "",
    val picture: String? = null
): Parcelable
