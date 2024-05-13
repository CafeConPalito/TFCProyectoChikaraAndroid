package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
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
import com.cafeconpalito.chikara.utils.LoginValidateUsername
import com.cafeconpalito.chikara.utils.UserPreferences
import com.cafeconpalito.chikara.utils.ValidateFields
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

//    @Inject
//    lateinit var userPreferences: UserPreferences

    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var validateFields: ValidateFields

//    @Inject
//    lateinit var validateFields: ValidateFields

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

        //Lo mismo que abajo pero resumido!
//        val userName = intent.getStringExtra(UserPreferences.KEY_USER_STR.name)
//        val password = intent.getStringExtra(UserPreferences.KEY_PASSWORD_STR.name)
//
//        if (userName != null) {
//            binding.etUserName.setText(userName)
//        }
//        if (password != null) {
//            binding.etPassword.setText(password)
//        }


        // Recuperar los datos del Intent y establecer en EditText si no son null
        intent.getStringExtra(UserPreferences.KEY_USER_STR.name)?.let {
            binding.etUserName.setText(it)
        }
        intent.getStringExtra(UserPreferences.KEY_PASSWORD_STR.name)?.let {
            binding.etPassword.setText(it)
        }

    }

    /**
     * Inicializa todos los procesos necesarios para que la vista funcione.
     */
    private fun initUI() {
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
        binding.btnRegister.setOnClickListener { goToRegisterActivity() }

        //Cuando Gana Foco
        binding.etUserName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) { // Al ganar foco
                clearErrorEtUserName()
            } else { // Al perder el foco
                if (validateFields.validateHaveBlankSpaces(binding.etUserName.text.toString())) {
                    //TODO: AÑADIR TOAST
                }
            }
        }

        //Cuando el texto se modifica
        binding.etUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                clearErrorEtUserName()
            } // Al modificar el texto
        })

        //Cuando Gana Foco
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtPassword()
            } else {
                if (validateFields.validateHaveBlankSpaces(binding.etPassword.text.toString())) {
                    //TODO: AÑADIR TOAST
                }
            }
        }

        //Cuando el texto se modifica
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                clearErrorEtPassword()
            } // Al modificar el texto
        })

        //Cuando se preciona ok o intro en el password intentara Logear!
        binding.etPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                logginAction() //Intenta logear!
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

    }

    /**
     * Te envia a la Activity de Registro.
     */
    private fun goToRegisterActivity() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    /**
     * Intenta realizar el login.
     */
    private fun logginAction() {
        clearError()
        if (!checkBlank()) { //Si los editText no estan en blanco. chechBlank se ocupa de pintar los errores

            val user =
                LoginValidateUsername()(binding.etUserName.text.toString()) // Valida el usuario
            val password =
                CypherTextToMD5()(binding.etPassword.text.toString()) //Cifra en MD5 el Password

            binding.pbLoggin.isVisible = true

            loginViewModel.launchLoginFlow(user, password);

        }

    }

    /**
     * Comprueba que los campos no esten en blanco y pinta los Hint en rojo si lo estubieran.
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
     * Limpia todos los errores de los campos. Edit Text.
     */
    private fun clearError() {
        clearErrorEtUserName()
        clearErrorEtPassword()
    }

    /**
     * Limpia los errores del EditText UserName.
     */
    private fun clearErrorEtUserName() {
        binding.etUserName.setHintTextColor(defaultHintColor)
        binding.etUserName.setTextColor(defaultEditTextColor)
    }

    /**
     * Limpia los errores del EditText Password.
     */
    private fun clearErrorEtPassword() {
        binding.etPassword.setHintTextColor(defaultHintColor)
        binding.etPassword.setTextColor(defaultEditTextColor)
    }

    /**
     * Inicializa el controlador para el cambio de estados.
     */
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

    /**
     * Cambio de Estado a Success. se pudo establecer la coneccion con el usuario y contraseña.
     * Guarda los datos del usuario en las user preferences y cambia de ventana.
     */
    private fun successState(it: LoginState.Success) {

        binding.pbLoggin.isVisible = false

        preferencesSaveUserLoginData(it.user, it.password)

        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    /**
     * Cambio de Estado a Error. no se pudo establecer un loggin.
     * Segun la respuesta es por Usuario o Password.
     */
    private fun errorState(it: LoginState.Error) {

        binding.pbLoggin.isVisible = false

        if (it.UserFounded) {
            binding.etPassword.setTextColor(errorEditTextColor)
            //TODO: la contraseña esta mal pinta lo que sea ...
            //Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_LONG).show()
        } else {
            binding.etUserName.setTextColor(errorEditTextColor)
            //TODO: no encontro al usuario pinta lo que sea ...
            //Toast.makeText(this, "Usuario No Existe", Toast.LENGTH_LONG).show()
        }

    }

    /**
     * Cambio de Estado a Loading.
     * No es necesario implementar nada en el.
     */
    private fun loadingState() {
        //NO HACE FALTA HACER NADA EN PRINCIPIO
    }

    /**
     * Guarda los datos del Usuario para realizar el Login en UserPreferences.
     */
    private fun preferencesSaveUserLoginData(user: String, password: String) {
        //GUARDA LOS DATOS
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.savePreference(UserPreferences.KEY_PASSWORD_STR, password)
            userPreferences.savePreference(UserPreferences.KEY_USER_STR, user)
        }
    }

}