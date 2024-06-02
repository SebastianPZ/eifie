package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalhealth.eifie.domain.entities.Role

@Entity
data class LocalUser(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "profileId") val profileId: Long,
    @ColumnInfo(name = "psychologistAssigned") val psychologistAssigned: Long,
    @ColumnInfo(name = "firstname") val firstName: String?,
    @ColumnInfo(name = "lastname") val lastName: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "username") val userName: String?,
    @ColumnInfo(name = "birthdate") val birthDate: String?,
    @ColumnInfo(name = "picture") val picture: String?,
    @ColumnInfo(name = "role") val role: Int?,
    @Embedded val hospital: LocalHospital?
)

internal fun LocalUser.getRole(): Role {
    return when(this.role) {
        1 -> Role.PSYCHOLOGIST
        else -> Role.PATIENT
    }
}