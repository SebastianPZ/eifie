package com.mentalhealth.eifie.data.mock

import com.mentalhealth.eifie.domain.entities.Notification
import com.mentalhealth.eifie.domain.entities.Survey
import com.mentalhealth.eifie.ui.navigation.Router

object FormFakeData {

    fun getNotificationsData(): List<Notification> {
        return listOf(
            Notification(
                title = "Formulario diario",
                content = "",
                action = Router.FORM.route
            )
        )
    }

    fun getFormData(): Survey {
        return Survey(
            title = "Formulario diario",
            description = "Formulario elaborado para seguimiento diario de paciente.",
            questions = 10,
            minimumTime = 3,
            maxTime = 5
        )
    }

}