package com.mentalhealth.eifie.data.mock

import com.mentalhealth.eifie.data.models.response.Question
import com.mentalhealth.eifie.domain.entities.Form

object FormFakeData {

    fun getFormListData(): List<Form> {
        return listOf(
            Form(
                id = 1,
                name = "Formulario diario",
                description = "",
                questions = "10",
                time = "3 - 5 min"
            ),
            Form(
                id = 2,
                name = "Formulario semanal",
                description = "",
                questions = "20",
                time = "6 - 10 min"
            )
        )
    }

    fun getFormData(): Form {
        return Form(
            id = 1,
            name = "Formulario diario",
            description = "",
            questions = "10",
            time = "3 - 5 min"
        )
    }


    fun getQuestionsByForm(): List<Question> {
        return listOf(
            Question(
                questionId = 1,
                text= "Pregunta 1",
                answers = "0:Mal|1:Mas o menos|2:Bien|3:Muy bien|4:Okie Dokie"
            ),
            Question(
                questionId = 2,
                text= "Pregunta 2",
                answers = "0:Mal|1:Mas o menos|2:Bien|3:Muy bien|4:Okie Dokie"
            ),
            Question(
                questionId = 3,
                text= "Pregunta 3",
                answers = "0:Mal|1:Mas o menos|2:Bien|3:Muy bien|4:Okie Dokie"
            ),
            Question(
                questionId = 4,
                text= "Pregunta 4",
                answers = "0:Mal|1:Mas o menos|2:Bien|3:Muy bien|4:Okie Dokie"
            ),
            Question(
                questionId = 5,
                text= "Pregunta 5",
                answers = "0:Mal|1:Mas o menos|2:Bien|3:Muy bien|4:Okie Dokie"
            )
        )
    }


}