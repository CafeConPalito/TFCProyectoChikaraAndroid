package com.cafeconpalito.chikara.domain.model

import org.jetbrains.annotations.NotNull
import java.sql.Date

data class UserDto(

    val user_name: String,
    val email: String,
    val pwd: String,
    val first_name: String,
    val first_last_name: String,
    val second_last_name: String, //Optional
    val birthdate: Date,
    val account_creation: Date,
    val is_premium: Boolean //Optional

)
