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