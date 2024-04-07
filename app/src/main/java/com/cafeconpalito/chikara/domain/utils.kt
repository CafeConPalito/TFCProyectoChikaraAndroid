package com.cafeconpalito.chikara.domain

class utils {


    /**
     * Email Validator!
     */
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}