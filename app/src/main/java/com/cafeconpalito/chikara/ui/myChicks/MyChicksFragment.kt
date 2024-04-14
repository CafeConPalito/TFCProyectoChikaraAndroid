package com.cafeconpalito.chikara.ui.myChicks


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cafeconpalito.chikara.databinding.FragmentMyChicksBinding
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import com.cafeconpalito.chikara.utils.UserPreferences
import com.cafeconpalito.chikara.utils.UserPreferencesModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [MyChicksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyChicksFragment : Fragment() {
    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentMyChicksBinding? = null
    private val binding get() = _binding!!
    lateinit var userPreferences:UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyChicksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Le paso el contecto a User Preferences.
        userPreferences = UserPreferences(requireContext())
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnTestCIFRAR.setOnClickListener {
            launchTestCifrar()
        }
        binding.btnTestGUARDAR.setOnClickListener{
            launchTestGuardar()
        }
        binding.btnTestLEER.setOnClickListener{
            launchTestLeer()
        }
        binding.btnTestBORRAR.setOnClickListener {
            launchTestBorrar()
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
                    Log.i("TEST", userPreferenceModel.user)
                    Log.i("TEST", "" + userPreferenceModel.password)
                }
            }
        }
    }

    private fun launchTestGuardar() {
        //GUARDA LOS DATOS
        CoroutineScope(Dispatchers.IO).launch {

            userPreferences.savePreference(UserPreferences.KEY_PASSWORD_STR, "81dc9bdb52d04dc20036dbd8313ed055")
            userPreferences.savePreference(UserPreferences.KEY_USER_STR, "@daniel")

        }
    }
    private fun launchTestCifrar() {
        val cypherMD5 = CypherTextToMD5()
        val cypher: String = cypherMD5("1234")
        Log.i("TEST", "Cifrado MD5:" + cypher)

    }
}