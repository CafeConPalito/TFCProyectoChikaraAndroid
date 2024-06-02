package com.cafeconpalito.chikara.ui.nakama

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cafeconpalito.chikara.databinding.FragmentNakamaBinding
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import com.cafeconpalito.chikara.utils.UserPreferences
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
    lateinit var chickUseCases: ChickUseCases

    //@Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNakamaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {

        //TODO USER PREFERENCES PRUEBAS ->
        binding.btnTestCIFRAR.setOnClickListener {
            launchTestCifrar()
        }
        binding.btnTestGUARDAR.setOnClickListener {
            launchTestGuardar()
        }
        binding.btnTestLEER.setOnClickListener {
            launchTestLeer()
        }
        binding.btnTestBORRAR.setOnClickListener {
            launchTestBorrar()
        }

        //TODO CHICK PRUEBAS ->

        binding.btnFindChick.setOnClickListener {
            launchFindTopChick()
        }

    }

    private fun launchFindTopChick() {

        lifecycleScope.launch {
            val listTopChicks = chickUseCases.getTopChicks()

            for( x in listTopChicks){
                Log.i("Chick", x.toString())
            }
        }
    }

    private fun launchTestBorrar() {

        CoroutineScope(Dispatchers.IO).launch {

            userPreferences.deltePreference(UserPreferences.KEY_USER_STR)
            userPreferences.deltePreference(UserPreferences.KEY_PASSWORD_STR)

        }

    }

    private fun launchTestLeer() {
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.getSettings().collect { userPreferenceModel ->
                if (userPreferenceModel != null) {
                    Log.i("TEST", userPreferenceModel.userName)
                    Log.i("TEST", "" + userPreferenceModel.password)
                }
            }
        }
    }

    private fun launchTestGuardar() {
        //GUARDA LOS DATOS
        CoroutineScope(Dispatchers.IO).launch {

            userPreferences.savePreference(
                UserPreferences.KEY_PASSWORD_STR,
                "81dc9bdb52d04dc20036dbd8313ed055"
            )
            userPreferences.savePreference(UserPreferences.KEY_USER_STR, "@daniel")

        }
    }

    private fun launchTestCifrar() {
        val cypherMD5 = CypherTextToMD5()
        val cypher: String = cypherMD5("1234")
        Log.i("TEST", "Cifrado MD5:" + cypher)

    }

}