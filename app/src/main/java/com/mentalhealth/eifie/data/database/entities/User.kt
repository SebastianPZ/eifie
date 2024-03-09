package com.mentalhealth.eifie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "profileId") val profileId: Int,
    @ColumnInfo(name = "firstname") val firstName: String?,
    @ColumnInfo(name = "lastname") val lastName: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "username") val userName: String?,
    @ColumnInfo(name = "birthdate") val birthDate: String?,
    @ColumnInfo(name = "picture") val picture: String?,
    @ColumnInfo(name = "role") val role: Int?,
    @Embedded val hospital: Hospital?
)