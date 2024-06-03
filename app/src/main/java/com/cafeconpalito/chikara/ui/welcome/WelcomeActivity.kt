package com.cafeconpalito.chikara.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityWelcomeBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    //Necesario para Que initUIState Funcione
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    /**
     * Inicializa todos los procesos necesarios para que la vista funcione.
     */
    private fun initUI() {

        initUIState()
        initUIlaunchLoginFlow()

    }


    /**
     * Inicializa el controlador para el cambio de estados.
     */
    private fun initUIState() {
        //Hilo que esta pendiente de la vida de la VIEW, si la view muere el para!
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                welcomeViewModel.state.collect {
                    //Siempre que cambien el estado hara lo siguiente
                    when (it) {
                        //it es la informacion del estado que puede contener informacion.
                        //Cada cambio de estado llama a su metodo
                        WelcomeState.Loading -> loadingState()
                        is WelcomeState.Error -> errorState(it)
                        is WelcomeState.Success -> successState(it)
                    }
                }
            }
        }
    }

    /**
     * Cambio de Estado a Success.
     */
    private suspend fun successState(it: WelcomeState.Success) {

        binding.pbWelcome.isVisible = false
        binding.tvWelcomeMessage.text = getText(R.string.text_welcome_message2)

        delay(2000L)
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    /**
     * Cambio de Estado a Error.
     * En caso de error te envia al Loggin, espera unos segundos
     */
    private suspend fun errorState(it: WelcomeState.Error) {
        binding.pbWelcome.isVisible = false
        binding.tvWelcomeMessage.text = getText(R.string.text_welcome_message)

        delay(2000L)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

//        SI QUIERES QUE AL DAR ERROR TE ENVIE A HOME, COMENTA LO DE ARRIBA Y ACTIVA ESTO
//        val intent =  Intent(this, HomeActivity::class.java)
//        startActivity(intent)

    }

    /**
     * Cambio de Estado a Loading.
     * No tiene que hacer nada en principio.
     */
    private fun loadingState() {

    }

    /**
     * Lee desde el fichero de properties e intenta logear.
     */
    private fun initUIlaunchLoginFlow() {

        welcomeViewModel.launchLoginFlow(applicationContext)

    }


}