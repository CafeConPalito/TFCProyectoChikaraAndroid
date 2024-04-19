package com.cafeconpalito.chikara.utils

class LoginValidateUsername {

    /**
     * Comprueba que un usuario es correcto, validando el mail y el usuario. en caso de no tener @ al inicio del String lo añade.
     *
     */
    operator fun invoke(user:String):String{

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            return user;
        }else if (!user.startsWith('@')){
            return "@"+user;
        }
        return user;
    }

}