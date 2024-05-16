package com.cafeconpalito.chikara.domain.model

import java.sql.Date

data class UserDto(

    val user_name: String,
    val email: String,
    val pwd: String,
    val first_name: String,
    val first_last_name: String,
    val second_last_name: String, //Optional
    val birthdate: String,
    //val birthdate: Date,
    //val account_creation: Date,
    val is_premium: Boolean = false

)

//    {
//        "user_name": "string",
//        "email": "string",
//        "pwd": "string",
//        "first_name": "string",
//        "first_last_name": "string",
//        "second_last_name": "string",
//        "birthdate": "2024-04-22",
//        "is_premium": true
//    }