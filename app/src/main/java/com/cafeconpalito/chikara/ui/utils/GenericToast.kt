package com.cafeconpalito.chikara.ui.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.CustomToastLayoutBinding

class GenericToast {

    companion object {

        /**
         * Generate a generic toast.
         * @param context Application Context
         * @param message String to display
         * @param toastLength Duration of Toast
         * @param isError type of Toast
         */
        fun generateToast(
            context: Context,
            message: String,
            toastLength: Int,
            isError: Boolean
        ): Toast {

            val binding = CustomToastLayoutBinding.inflate(LayoutInflater.from(context))

            //Seteamos el mensaje
            binding.customToastText.text = message

            // Setear el clor del texto
            binding.customToastText.setTextColor(
                if (isError) ContextCompat.getColor(context, R.color.toast_text_error)
                else ContextCompat.getColor(context, R.color.toast_text)
            )

            binding.customToastText.setBackgroundResource(R.color.toast_background)

            // Creamos un objeto Toast
            val toast = Toast(context)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = toastLength
            toast.view = binding.root
            return toast
        }
    }

}