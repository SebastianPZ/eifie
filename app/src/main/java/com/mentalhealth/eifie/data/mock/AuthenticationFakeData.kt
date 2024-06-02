package com.mentalhealth.eifie.data.mock

import com.mentalhealth.eifie.data.models.response.UserPatientResponse
import com.mentalhealth.eifie.data.models.response.UserResponse

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
    fun registerUser(): UserPatientResponse {
        return UserPatientResponse(

        )
    }

}