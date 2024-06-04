package com.cafeconpalito.chikara.domain.model

data class UserDto(

    val id: String? = null,
    var user_name: String,
    var email: String,
    var pwd: String,
    val first_name: String,
    val first_last_name: String,
    val second_last_name: String, //Optional
    val birthdate: String,
    //val birthdate: Date,
    //val account_creation: Date,
    val is_premium: Boolean = false

)
