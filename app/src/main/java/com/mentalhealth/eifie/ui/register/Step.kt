package com.mentalhealth.eifie.ui.register

class Step(
    val order: Int,
    val title: String
) {
    companion object {
        const val FIRST: Int = 0
        const val SECOND = 1
        const val THIRD = 2
    }
}


/*data class ROLE(val id: Int = 1, val name: String = "Rol"): Step(id, name)
data class PERSONAL(val id: Int = 1, val name: String = "Datos"): Step(id, name)
data class USER(val id: Int = 1, val name: String = "Rol"): Step(id, name)
data class PERSONAL("Datos"),
data class USER("Usuario"),
CODE_VALIDATION("Validación código"),
PSYCHOLOGIST_VALIDATION("Validación psicólogo")*/