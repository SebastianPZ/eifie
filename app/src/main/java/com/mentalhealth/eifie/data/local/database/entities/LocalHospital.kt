package com.mentalhealth.eifie.data.local.database.entities

import androidx.room.ColumnInfo

data class LocalHospital(
    @ColumnInfo("hospital_id") val hospitalId: Int?,
    @ColumnInfo("hospital_name") val hospitalName: String?
)
