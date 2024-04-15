package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.domain.utils
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.nakama.NakamaState
import com.cafeconpalito.chikara.ui.nakama.NakamaViewModel
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    val errorColor = ContextCompat.getColor(this, R.color.error_edit_text)
    val defaultColor = ContextCompat.getColor(this, R.color.default_edit_text)
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        clearError()
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener { logginAction() }
        binding.btnRegister.setOnClickListener { goToRegister() }

    }

    private fun goToRegister() {
        val intent =  Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun logginAction() {
        clearError()
        if (!chekBlank()){
            val userInput = binding.etUserName.text.toString()
            val passwordInput = binding.etPassword.text.toString()
            val cypherTextToMD5 = CypherTextToMD5()
            val passwordAuthentication = cypherTextToMD5(passwordInput)
        }



    }

    private fun chekBlank(): Boolean {
        var errorUser = false
        var errorPassword = false
        if (binding.etUserName.text.isBlank()){
            binding.etUserName.setHintTextColor(errorColor)
            errorUser = true
        }
        if (binding.etPassword.text.isBlank()){
            binding.etPassword.setHintTextColor(errorColor)
            errorPassword = true
        }
        return errorUser || errorPassword
    }

    private fun clearError() {
        binding.etUserName.setHintTextColor(defaultColor);
        binding.etPassword.setHintTextColor(defaultColor);
    }

    private val loginViewModel: LoginViewModel by viewModels()

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
        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun errorState(it: LoginState.Error) {
        TODO("Not yet implemented")
        it.PasswordMatched
        it.UserFounded
    }

    private fun loadingState() {
        TODO("Not yet implemented")
    }

}