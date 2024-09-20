package com.mentalhealth.eifie.domain.entities

import com.mentalhealth.eifie.util.ValidateText
import com.mentalhealth.eifie.util.emailRules
import com.mentalhealth.eifie.util.passwordRules

class UserData(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
) {
    fun isValid(password: String): Boolean {
        return isValidEmail() && isValidPassword(password) && password == confirmPassword
    }

    private fun isValidEmail(): Boolean {
        (ValidateText(email) checkWith emailRules).let {
            return it.isSuccess
        }
    }

    private fun isValidPassword(password: String): Boolean {
        (ValidateText(password) checkWith passwordRules).let {
            return it.isSuccess
        }
    }
}