package com.mentalhealth.eifie.domain.entities

class Supporter(
    val id: Long? = null,
    val user: Long = 0,
    var name: String = "",
    var config: String = "",
    var photo: String? = null,
)