package com.mentalhealth.eifie.data.mock

import com.mentalhealth.eifie.data.network.models.response.PatientResponse
import com.mentalhealth.eifie.data.network.models.response.UserResponse

object AuthenticationFakeData {

    /** Mock return user when login with values **/
    fun loginUser(): UserResponse {
        return UserResponse(
            firstName = "Sebastian",
            lastName = "Pinillos",
            role = "P"
        )
    }

    /** Mock return user when register with values **/
    fun registerUser(): PatientResponse {
        return PatientResponse(

        )
    }

}