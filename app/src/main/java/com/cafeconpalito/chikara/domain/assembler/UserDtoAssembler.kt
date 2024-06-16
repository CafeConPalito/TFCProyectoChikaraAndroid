package com.cafeconpalito.chikara.domain.assembler

import android.net.Uri
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.utils.CypherTextToMD5

class UserDtoAssembler {

    /**
     * Build UserDto
     * password is pass whitout Cypher To MD5
     */
    fun buildDto(
        userName: String,
        email: String,
        pass: String,
        firstName: String,
        firstLastName: String,
        secondLastName: String,
        birthdate: String
    ): UserDto {

        val cypherTextToMD5 = CypherTextToMD5()
        val userDto = UserDto(
            user_name = "@$userName",
            email = email,
            pwd = cypherTextToMD5(pass),
            first_name = firstName.trim().replace(Regex("\\s+"), " "),
            first_last_name = firstLastName.trim().replace(Regex("\\s+"), " "),
            second_last_name = secondLastName.trim()
                .replace(Regex("\\s+"), " "), // Puedes asignar null si es opcional
            birthdate = birthdate
        )

        return userDto
    }
}