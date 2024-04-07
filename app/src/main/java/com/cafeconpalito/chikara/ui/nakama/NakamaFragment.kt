package com.cafeconpalito.chikara.ui.nakama

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentNakamaBinding
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NakamaFragment : Fragment() {

    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentNakamaBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var getLoginUseCase : GetLoginUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNakamaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        val user = "@usuario1"
        val password = "a722c63db8ec8625af6cf71cb8c2d939"

        nakamaViewModel.tryLogin(user,password)

    }

    private fun initUI() {
        //loggin() // sin modificar view
        initUIState()

    }



    /**
     * Prueba de Login
     * OJO NO PERMITE MODIFICAR LA VIEW (BINDING)
     */
    fun loggin() {

        val user = "@usuario1"
        val password = "a722c63db8ec8625af6cf71cb8c2d939"


        CoroutineScope(Dispatchers.IO).launch {

            if ( getLoginUseCase(user, password)) {
                Log.i("Fragment Nakama: ", " Login Correcto")

            } else {
                Log.i("Fragment Nakama: ", " Login Incorrecto")

            }
        }

    }


    //Necesario para Que initUIState Funcione
    //SE TENDRIA QUE DECLARAR ARRIBA!
    private val nakamaViewModel:NakamaViewModel by viewModels()

    //Parte de el inicio de la UI para que este pendiende si cambia el estado al llamar al metodo.
    private fun initUIState() {

        //Hilo que esta pendiente de la vida de la VIEW, si la view muere el para!
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                nakamaViewModel.state.collect {
                    //Siempre que cambien el estado hara lo siguiente
                    when (it) {
                        //it es la informacion del estado que puede contener informacion.
                        //Cada cambio de estado llama a su metodo
                        NakamaState.Loading -> loadingState()
                        is NakamaState.Error -> errorState(it)
                        is NakamaState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(it: NakamaState.Success) {
        //Lo que quieras hacer cuando sea OK
        binding.pbLoggin.isVisible = false
        binding.tvPrueba.text = it.toString()
    }

    private fun errorState(it: NakamaState.Error) {
        //Lo que quieras hacer cuando sea Error
        binding.pbLoggin.isVisible = false
        binding.tvPrueba.text = it.toString()
    }

    private fun loadingState() {
        binding.pbLoggin.isVisible = true
        //Lo que quieras hacer cuando esta cargando
    }


}