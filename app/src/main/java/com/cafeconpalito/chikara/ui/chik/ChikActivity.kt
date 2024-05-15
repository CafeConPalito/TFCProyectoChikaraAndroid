package com.cafeconpalito.chikara.ui.chik

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityChikBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChikBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initEditables()
        initListeners()
        initUserValuesOnThisChick()
    }

    /**
     * Modifica la vista del Chick en funcion de las anteries interacciones de este en el.
     */
    private fun initUserValuesOnThisChick() {
        //TODO("Not yet implemented")
        //SI YA TENIA LIKE QUE LO PINTE DE ROJO SINO
        //LEER DTO RECIBIDO Y PINTAR!
    }

    /**
     * vuelve visibles los iconos para editar y borrar chick.
     */
    private fun initEditables() {
        //TODO FALTA AÑADIR EL IF PARA COMPROBAR SI EL USUARIO ES EL DUEÑO
        binding.ivEdit.isVisible = true
        binding.ivDelete.isVisible = true

    }

    private fun initListeners() {

        binding.ivLike.setOnClickListener { likeChick() }
        binding.tvAutor.setOnClickListener { goToAutorChicks() }
        binding.ivEdit.setOnClickListener { goToEditChick() }
        binding.ivDelete.setOnClickListener { erraseChick() }

    }

    /**
     * Borra el Chick, Pregunta al Usuario antes si realmente lo quiere eliminar.
     *
     */
    private fun erraseChick() {
        //Lanzar la pregunta al Usuario.
        //if -> ok
            //Mensaje de Borrado.
            //Cierra el chick y se va a Home.
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Confirmación")
            .setMessage(R.string.AlertDialogMessage)
            .setPositiveButton(R.string.AlertDialogAccept) { dialog, which ->

                //TODO BORRAR EN LA API!

                dialog.dismiss()
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(R.string.AlertDialogCancel) { dialog, which ->
                dialog.dismiss() // Cierra el diálogo si el usuario cancela
            }
            .show()

    }

    /**
     * LLeva al editor de Chick
     */
    private fun goToEditChick() {
        //TODO("Not yet implemented")
    }

    /**
     * Nos envia a la lista de Chicks de un Autor
     */
    private fun goToAutorChicks() {
        //Capturar el dato del Autor y lanzar un intent con una Recicler View con los chicks del autor!
        //TODO("Not yet implemented")
    }


    /**
     * Envia los datos a la API PARA AÑADIR LIKE O QUITARLO.
     */
    private fun likeChick() {

        //TODO("Not yet implemented")

        //IF -> NO TENIA LIKE DEL USUARIO
            //SUMAR 1 -- Logica Interna
            //Pintar de color el Icono
            //ENVIAR EL Like a la API.
        //ELSE
            //RESTAR 1 -- Logica interna
            //Pintar el Icono
            //QUITAR EL Like a la API

    }

}