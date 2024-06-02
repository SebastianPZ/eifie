package com.mentalhealth.eifie.domain.entities

class PersonalData(
    var firstname: String = "",
    var lastname: String = "",
    var birthdate: String = "",
    var hospital: Int? = null
) {
    fun isValid(): Boolean {
        return firstname.isNotBlank() && lastname.isNotBlank() && birthdate.isNotBlank()
    }
}
