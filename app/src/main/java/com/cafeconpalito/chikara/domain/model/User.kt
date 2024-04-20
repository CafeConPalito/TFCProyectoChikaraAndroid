package com.cafeconpalito.chikara.domain.model

import java.sql.Date
data class User(

    val id: String,
    val user_name: String,
    val email: String,
    val password: String,
    val first_name: String,
    val first_last_name: String,
    val second_last_name: String,
    val birthdate: Date,
    val account_creation: Date,
    val is_premium: Boolean

)
