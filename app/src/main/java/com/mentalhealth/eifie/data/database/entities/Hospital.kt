package com.mentalhealth.eifie.data.database.entities

import androidx.room.ColumnInfo

data class Hospital(
    @ColumnInfo("hospital_id") val hospitalId: Int?,
    @ColumnInfo("hospital_name") val hospitalName: String?
)
