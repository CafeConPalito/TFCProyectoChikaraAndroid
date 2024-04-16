package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import com.cafeconpalito.chikara.utils.ValidateUsername
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

//    val errorColor:I
//    val defaultColor

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        errorColor = ContextCompat.getColor(this, R.color.error_edit_text)
//        defaultColor = ContextCompat.getColor(this, R.color.default_edit_text)


        Log.i("TEST", "CREANDO LOGGIN ACTIVITY")

        initUI()
    }

    private fun initUI() {
        clearError()
        //initUIState()
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
        if (!chekBlank()){ //Si los editText no estan en blanco
            val user = ValidateUsername()(binding.etUserName.text.toString()) // Valida el usuario
            val password = CypherTextToMD5()(binding.etPassword.text.toString()) //Cifra en MD5 el Password

            //TODO LLAMAR AL METODO QUE INTENTA REALIZAR EL LOGGIN
            loginViewModel.launchLoginFlow(user,password);
        }

    }

    private fun chekBlank(): Boolean {
        var errorUser = false
        var errorPassword = false
        if (binding.etUserName.text.isBlank()){
//            binding.etUserName.setHintTextColor(errorColor)
            errorUser = true
        }
        if (binding.etPassword.text.isBlank()){
//            binding.etPassword.setHintTextColor(errorColor)
            errorPassword = true
        }
        return errorUser || errorPassword
    }

    private fun clearError() {
//        binding.etUserName.setHintTextColor(defaultColor);
//        binding.etPassword.setHintTextColor(defaultColor);
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