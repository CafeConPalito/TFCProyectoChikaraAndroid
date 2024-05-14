package com.cafeconpalito.chikara.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject


@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
class ValidateFields @Inject constructor() {

    /**
     * Valida el Email utilizando un metodo de Android
     */
    @Provides
    fun validEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    /**
     * Valida e userName.
     * Minimo 3 Caracteres y No puede contener @ (Arroba)
     * return True si es valido
     */
    @Provides
    fun validUserName(user: String): Boolean {
        val minLength = 3
        val notContain = Regex("[@]")
        return user.length >= minLength &&
                !user.contains(notContain) //Negacion para que no lo contenga

    }

    /**
     * Valida el Password.
     * Tiene que contener minimo 8 Caracteres y al menos:
     * una Mayuscula, una Minuscula y un numero.
     */
    @Provides
    fun validatePassword(password: String): Boolean {
        val minLength = 8
        val uppercaseRegex = Regex("[A-Z]")
        val lowercaseRegex = Regex("[a-z]")
        val digitRegex = Regex("\\d")

        return password.length >= minLength &&
                password.contains(uppercaseRegex) &&
                password.contains(lowercaseRegex) &&
                password.contains(digitRegex)
    }

    /**
     * Comprueba que el Password y PasswordRepeat son iguales.
     * Si son iguales devuelve True
     */
    @Provides
    fun validatePasswordsMatches(passwordA: String, passwordB: String): Boolean {
        return passwordA.equals(passwordB)
    }

    /**
     * Comprueba si el texto tiene espacios en blanco
     * Si es asi devuelve True
     */
    @Provides
    fun validateHaveBlankSpaces(text: String): Boolean {
        return text.contains(" ")
    }

    /**
     * Comprueba si el texto tiene espacios en blanco
     * Si es asi devuelve True
     */
    @Provides
    fun completeUserName(userName: String): String {
        if(!userName.startsWith('@')){
            return "@"+userName
        }

        return userName
    }


}