package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import com.cafeconpalito.chikara.utils.UserPreferences
import com.cafeconpalito.chikara.utils.ValidateUsername
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    lateinit var userPreferences: UserPreferences

    //LLevar el metodo initColor()
    private var defaultEditTextColor = 0
    private var errorEditTextColor = 0
    private var defaultHintColor = 0
    private var errorHintColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        initUI()

    }


    private fun initUI() {
        clearError()
        initListeners()
        initUIState()
        initColors()
    }

    private fun initColors() {

        defaultEditTextColor = ContextCompat.getColor(this, R.color.default_edit_text)
        errorEditTextColor = getColor(R.color.error_edit_text)
        defaultHintColor = getColor(R.color.default_hint_edit_text)
        errorHintColor = getColor(R.color.error_hint_edit_text)

    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener { logginAction() }
        binding.btnRegister.setOnClickListener { goToRegister() }

    }

    private fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun logginAction() {
        clearError()
        if (!checkBlank()) { //Si los editText no estan en blanco. chechBlank se ocupa de pintar los errores

            val user = ValidateUsername()(binding.etUserName.text.toString()) // Valida el usuario
            val password =
                CypherTextToMD5()(binding.etPassword.text.toString()) //Cifra en MD5 el Password

            binding.pbLoggin.isVisible = true

            loginViewModel.launchLoginFlow(user, password);

        }

    }

    /**
     * Comprueba que los campos no esten en blanco y pinta los Hint en rojo si lo estubieran
     */
    private fun checkBlank(): Boolean {
        var errorUser = false
        var errorPassword = false
        if (binding.etUserName.text.isBlank()) {
            binding.etUserName.setHintTextColor(errorHintColor)
            errorUser = true
        }
        if (binding.etPassword.text.isBlank()) {
            binding.etPassword.setHintTextColor(errorHintColor)
            errorPassword = true
        }
        return errorUser || errorPassword
    }

    /**
     * Limpia todos los errores de los campos. Edit Text
     */
    private fun clearError() {
        clearErrorEtUserName()
        clearErrorEtPassword()
    }

    /**
     * Limpia los errores del EditText UserName
     */
    private fun clearErrorEtUserName() {
        binding.etUserName.setHintTextColor(defaultHintColor)
        binding.etUserName.setTextColor(defaultEditTextColor)
    }

    /**
     * Limpia los errores del EditText Password
     */
    private fun clearErrorEtPassword() {
        binding.etPassword.setHintTextColor(defaultHintColor)
        binding.etPassword.setTextColor(defaultEditTextColor)
    }


    //Parte de el inicio de la UI para que este pendiende si cambia el estado al llamar al metodo.
    private fun initUIState() {

        //Hilo que esta pendiente de la vida de la VIEW, si la view muere el para!
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect {
                    //Siempre que cambien el estado hara lo siguiente
                    when (it) {
                        //it es la informacion del estado que puede contener informacion.
                        //Cada cambio de estado llama a su metodo
                        LoginState.Loading -> loadingState()
                        is LoginState.Error -> errorState(it)
                        is LoginState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun successState(it: LoginState.Success) {

        binding.pbLoggin.isVisible = false

        preferencesSaveUserLoginData(it.user, it.password)

        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    private fun errorState(it: LoginState.Error) {

        binding.pbLoggin.isVisible = false

        if (it.UserFounded) {
            //TODO: la contraseña esta mal pinta lo que sea ...
            Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_LONG).show()
        } else {
            //TODO: no encontro al usuario pinta lo que sea ...
            Toast.makeText(this, "Usuario No Existe", Toast.LENGTH_LONG).show()
        }

    }

    private fun loadingState() {
        //NO HACE FALTA HACER NADA EN PRINCIPIO
        //TODO("Not yet implemented")
    }

    private fun preferencesSaveUserLoginData(user: String, password: String) {
        //GUARDA LOS DATOS
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.savePreference(UserPreferences.KEY_PASSWORD_STR, password)
            userPreferences.savePreference(UserPreferences.KEY_USER_STR, user)
        }
    }

}