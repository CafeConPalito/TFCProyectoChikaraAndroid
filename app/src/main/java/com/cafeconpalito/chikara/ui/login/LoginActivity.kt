package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    //val defaultColor = getColor(R.color.default_edit_text)
    //val errorColor = getColor(R.color.error_edit_text)
    //val colorEjemplo = ContextCompat.getColor(this@LoginActivity, R.color.error_edit_text)

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    lateinit var userPreferences:UserPreferences

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
        if (!checkBlank()){ //Si los editText no estan en blanco
            val user = ValidateUsername()(binding.etUserName.text.toString()) // Valida el usuario
            val password = CypherTextToMD5()(binding.etPassword.text.toString()) //Cifra en MD5 el Password

            binding.pbLoggin.isVisible = true

            loginViewModel.launchLoginFlow(user,password);

        }else{
            //TODO SI ESTA EN BLANCO PINTAR LOS ERRORES!
        }

    }

    private fun checkBlank(): Boolean {
        var errorUser = false
        var errorPassword = false
        if (binding.etUserName.text.isBlank()){
            //binding.etUserName.setHintTextColor(errorColor)
            errorUser = true
        }
        if (binding.etPassword.text.isBlank()){
            //binding.etPassword.setHintTextColor(errorColor)
            errorPassword = true
        }
        return errorUser || errorPassword
    }

    private fun clearError() {
        binding.etUserName.setHintTextColor(getColor(R.color.hint_edit_text));
        binding.etPassword.setHintTextColor(getColor(R.color.hint_edit_text));
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

        preferencesSaveUserLoginData(it.user,it.password)

        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    private fun errorState(it: LoginState.Error) {

        binding.pbLoggin.isVisible = false

        if (it.UserFounded){
            //TODO: la contraseña esta mal pinta lo que sea ...
            Toast.makeText(this,"Contraseña Incorrecta",Toast.LENGTH_LONG).show()
        } else{
            //TODO: no encontro al usuario pinta lo que sea ...
            Toast.makeText(this,"Usuario No Existe",Toast.LENGTH_LONG).show()
        }

    }

    private fun loadingState() {
        //NO HACE FALTA HACER NADA EN PRINCIPIO
        //TODO("Not yet implemented")
    }

    private fun preferencesSaveUserLoginData(user:String, password:String) {
        //GUARDA LOS DATOS
        CoroutineScope(Dispatchers.IO).launch {

            userPreferences.savePreference(UserPreferences.KEY_PASSWORD_STR, password)
            userPreferences.savePreference(UserPreferences.KEY_USER_STR, user)

        }
    }

}