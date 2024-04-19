package com.cafeconpalito.chikara.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.databinding.ActivityRegisterBinding
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.RegisterValidateFields
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity @Inject constructor(private val registerValidateFields: RegisterValidateFields): AppCompatActivity() {

    //TODO: CAMBIAR EL BIRD DATE DE UN EDIT TEXT A UN DATE PICKER!

    private lateinit var binding: ActivityRegisterBinding

    //LLevar el metodo initColor()
    private var defaultEditTextColor = 0
    private var errorEditTextColor = 0
    private var defaultHintColor = 0
    private var errorHintColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

    }

    private fun initUI() {

        initColors()
        initListeners()

    }
    private fun initColors() {

        defaultEditTextColor = ContextCompat.getColor(this, R.color.default_edit_text)
        errorEditTextColor = getColor(R.color.error_edit_text)
        defaultHintColor = getColor(R.color.default_hint_edit_text)
        errorHintColor = getColor(R.color.error_hint_edit_text)

    }
    private fun initListeners() {
        binding.btnLogin.setOnClickListener { goToLoginActivity() }
        binding.btnRegister.setOnClickListener { tryToRegister() }
    }

    private fun tryToRegister() {

        //Limpio los errores al dar al boton de registro.
        clearErrors()

        if (!registerValidateFields.validUserName(binding.etUserName.text.toString())){

        }
        if (!registerValidateFields.validEmail(binding.etEmail.text.toString())){

        }
        if (!registerValidateFields.validatePassword(binding.etPassword.text.toString())){

        }

    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun clearErrors(){

        clearErrorEtUserName()
        clearErrorEtEmail()
        clearErrorEtFirstName()
        clearErrorEtFirstLastName()
        clearErrorEtEscondLastName()
        clearErrorEtPassword()
        clearErrorEtPasswordRepeat()
        //TODO FALTARIA AÑADIR EL CLEAR ERRORS DEL DATE PICKER!
    }

    private fun clearErrorEtUserName() {
        binding.etUserName.setHintTextColor(defaultHintColor)
        binding.etUserName.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtEmail() {
        binding.etEmail.setHintTextColor(defaultHintColor)
        binding.etEmail.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtFirstName() {
        binding.etFirstName.setHintTextColor(defaultHintColor)
        binding.etFirstName.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtFirstLastName() {
        binding.etFirstLastName.setHintTextColor(defaultHintColor)
        binding.etFirstLastName.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtEscondLastName() {
        binding.etScondLastName.setHintTextColor(defaultHintColor)
        binding.etScondLastName.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtPassword() {
        binding.etPassword.setHintTextColor(defaultHintColor)
        binding.etPassword.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorEtPasswordRepeat() {
        binding.etPasswordRepeat.setHintTextColor(defaultHintColor)
        binding.etPasswordRepeat.setTextColor(defaultEditTextColor)
    }

}