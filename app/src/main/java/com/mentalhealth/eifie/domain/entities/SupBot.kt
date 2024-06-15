package com.mentalhealth.eifie.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Supporter(
    var id: Long? = null,
    val user: Long = 0,
    var name: String = "",
    var config: String = "",
    var photo: String? = null,
) : Parcelable