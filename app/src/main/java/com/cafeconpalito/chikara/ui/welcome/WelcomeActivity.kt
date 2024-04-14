package com.cafeconpalito.chikara.ui.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
    private val welcomeViewModel:WelcomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {

        initUIState()

        initUIlaunchLoginFlow()

    }



    //Parte de el inicio de la UI para que este pendiende si cambia el estado al llamar al metodo.
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

    private suspend fun successState(it: WelcomeState.Success) {

        binding.pbWelcome.isVisible = false
        binding.tvWelcomeMessage.text = "Lo importante no es lo que te prometes, sino lo que cumples"

        delay(2000L)
        val intent =  Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    /**
     * En caso de error te envia al Loggin, espera unos segundos
     */
    private suspend fun errorState(it: WelcomeState.Error) {
        binding.pbWelcome.isVisible = false
        binding.tvWelcomeMessage.text = "Imposible Conectar"
    
        delay(2000L)
        //TODO: TE ENVIA A EL LOGIN
        val intent =  Intent(this, LoginActivity::class.java)
        startActivity(intent)

        //TODO: SI QUIERES QUE AL DAR ERROR TE ENVIE A HOME, COMENTA LO DE ARRIBA Y ACTIVA ESTO
        //val intent =  Intent(this, HomeActivity::class.java)
        //startActivity(intent)

    }

    /**
     * Estado de carga, no tiene que hacer nada en principio
     */
    private fun loadingState() {

    }

    /**
     * Lee desde el fichero de properties e intenta loggear
     */
    private fun initUIlaunchLoginFlow() {

        welcomeViewModel.launchLoginFlow(applicationContext)

    }



}