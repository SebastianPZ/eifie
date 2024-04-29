package com.mentalhealth.eifie.domain.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Psychologist(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val picture: String
): Parcelable
