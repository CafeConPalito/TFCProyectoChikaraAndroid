package com.cafeconpalito.chikara.utils

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cafeconpalito.chikara.R

class GenericToast {

    companion object {
            fun generateToast(context: Context, mensaje: String, toastLength: Int, isError: Boolean): Toast {
                // Obtener el color del texto
                val textColor = if (isError) ContextCompat.getColor(context, R.color.toast_text_error) else ContextCompat.getColor(context, R.color.toast_text)

                // Creamos un objeto Toast
                val toast = Toast.makeText(context, mensaje, toastLength)
                toast.setGravity(Gravity.CENTER, 0, 0)

                // Personalizamos el diseño del Toast
                val toastLayout = toast.view as? TextView
                toastLayout?.apply {
                    setBackgroundResource(R.color.toast_background)
                    setTextColor(textColor)
                }
                return toast
            }
    }

}