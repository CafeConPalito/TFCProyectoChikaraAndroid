package com.cafeconpalito.chikara.ui.chik

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityChikBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.utils.UserSession
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ChikActivity : AppCompatActivity() {

    //TODO AÑADIR A LOS ITEMS PARA QUE LLAMEN A LA ACTIVITY CON LA INFORMACION DEL CHICK

//            val intent = Intent(this, OtraActivity::class.java).apply {
//            putExtra("chickDto", chickDto)
//        }
//        startActivity(intent)

    private lateinit var binding: ActivityChikBinding

    @Inject
    lateinit var chickUseCase: ChickUseCases

    private var chickDto: ChickDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Obtenemos la informacion del Chick
        chickDto = intent.getSerializableExtra("chickDto") as? ChickDto

        if (chickDto != null) {
            //Si contiene informacion la pintamos!
        } else {
            //Si no recibe informacion en el chickDto nos envia al home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

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
        // SI YA TENIA LIKE QUE LO PINTE DE ROJO SINO
        // LEER DTO RECIBIDO Y PINTAR!
    }

    /**
     * vuelve visibles los iconos para editar y borrar chick.
     * Si el usuario es el dueño del chick
     */
    private fun initEditables() {
        if (UserSession.userUUID.equals(chickDto!!._id)) {
            binding.ivEdit.isVisible = true
            binding.ivDelete.isVisible = true
        }
    }

    private fun initListeners() {
        binding.ivLike.setOnClickListener { likeChick() }
        binding.tvAutor.setOnClickListener { goToAutorChicks() }
        binding.ivEdit.setOnClickListener { goToEditChick() }
        binding.ivDelete.setOnClickListener { eraseChick() }
    }

    /**
     * Borra el Chick, Pregunta al Usuario antes si realmente lo quiere eliminar.
     *
     */
    private fun eraseChick() {
        //Lanzar la pregunta al Usuario.
        //if -> ok
        //Mensaje de Borrado.
        //Cierra el chick y se va a Home.
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Confirmación")
            .setMessage(R.string.AlertDialogMessage)
            .setPositiveButton(R.string.AlertDialogAccept) { dialog, which ->

                dialog.dismiss()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            chickUseCase.deleteChick(chickDto!!._id)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                //ENVIA AL HOME
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