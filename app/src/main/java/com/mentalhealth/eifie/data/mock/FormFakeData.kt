package com.mentalhealth.eifie.data.mock

import com.mentalhealth.eifie.data.models.response.QuestionResponse
import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.ui.navigation.Router
import okhttp3.Route

object FormFakeData {

    fun getNotificationsData(): List<Notification> {
        return listOf(
            Notification(
                title = "Formulario semanal",
                content = "",
                action = Router.FORM.route
            )
        )
    }

    fun getFormData(): Notification {
        return Notification(
            title = "Formulario semanal",
            content = ""
        )
    }

}