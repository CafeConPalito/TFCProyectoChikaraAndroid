package com.cafeconpalito.chikara.ui.nakama

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentNakamaBinding
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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

    }

    private fun initUI() {
        loggin()
    }

    /**
     * Prueba de Login
     */
    fun loggin() {


        val user = "@usuario1"
        val password = "a722c63db8ec8625af6cf71cb8c2d939"


        //Hilo normal
        CoroutineScope(Dispatchers.IO).launch {


            if ( getLoginUseCase(user, password)) {
                Log.i("Fragment Nakama: ", " Login Correcto")

            } else {
                Log.i("Fragment Nakama: ", " Login Incorrecto")
            }
        }
    }



}