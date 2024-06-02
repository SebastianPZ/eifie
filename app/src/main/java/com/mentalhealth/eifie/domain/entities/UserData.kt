package com.mentalhealth.eifie.domain.entities

class UserData(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
) {
    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
                && password == confirmPassword
    }
}