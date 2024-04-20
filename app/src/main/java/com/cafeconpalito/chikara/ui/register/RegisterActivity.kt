package com.cafeconpalito.chikara.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityRegisterBinding
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.RegisterValidateFields
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    //@Inject constructor(private val registerValidateFields: RegisterValidateFields)

    //TODO: CAMBIAR EL BIRD DATE DE UN EDIT TEXT A UN DATE PICKER!

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var registerValidateFields: RegisterValidateFields

    //LLevar el metodo initColor()
    private var defaultEditTextColor = 0
    private var errorEditTextColor = 0
    private var defaultHintColor = 0
    private var errorHintColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerValidateFields = RegisterValidateFields()

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

        etUserNameListeners()
        etEmailListeners()
        etFirstNamelListeners()
        etFirstLastNameListeners()
        etSecondLastNameListeners()
        dpBithDateListener() //TODO FALTA EL LISTENER DEL DATE PICKER
        etPasswordListeners()
        etPasswordRepeatListeners()

    }


    private fun etUserNameListeners() {

        //Cuando Gana Foco
        binding.etUserName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtUserName()
            } else {
                //TODO AÑADIR VALIDAR
                //TODO AÑADIR BUSCAR SI EXISTE EN DB
            }
        }

        //Cuando el texto se modifica
        binding.etUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtUserName() } // Al modificar el texto
        })

    }

    private fun etEmailListeners() {

        //Cuando Gana Foco
        binding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtEmail()
            } else {
                //TODO AÑADIR VALIDAR
                //TODO AÑADIR BUSCAR SI EXISTE EN DB
            }
        }

        //Cuando el texto se modifica
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtEmail() } // Al modificar el texto
        })

    }


    private fun etFirstNamelListeners() {

        //Cuando Gana Foco
        binding.etFirstName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtFirstName()
            } else {
                //TODO AÑADIR VALIDAR
            }
        }

        //Cuando el texto se modifica
        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtFirstName() } // Al modificar el texto
        })

    }

    private fun etFirstLastNameListeners() {

        //Cuando Gana Foco
        binding.etFirstLastName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtFirstLastName()
            } else {
                //TODO AÑADIR VALIDAR
            }
        }

        //Cuando el texto se modifica
        binding.etFirstLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtFirstLastName() } // Al modificar el texto
        })

    }

    private fun etSecondLastNameListeners() {

        //Cuando Gana Foco
        binding.etSecondLastName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtSecondLastName()
            } else {
                //TODO AÑADIR VALIDAR
            }
        }

        //Cuando el texto se modifica
        binding.etSecondLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtSecondLastName() } // Al modificar el texto
        })

    }

    private fun dpBithDateListener() {
        //TODO("Not yet implemented")
    }

    private fun etPasswordListeners() {

        //Cuando Gana Foco
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtPassword()
            } else {
                //TODO AÑADIR QUE AL SALIR BUSQUE SI EL PASSWORD ES VALIDO
            }
        }

        //Cuando el texto se modifica
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtPassword() } // Al modificar el texto
        })

    }

    private fun etPasswordRepeatListeners() {

        //Cuando Gana Foco
        binding.etPasswordRepeat.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                clearErrorEtPasswordRepeat()
            } else {
                //TODO AÑADIR QUE AL SALIR BUSQUE SI EL PASSWORD ES VALIDO
            }
        }

        //Cuando el texto se modifica
        binding.etPasswordRepeat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) { clearErrorEtPasswordRepeat() } // Al modificar el texto
        })

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
        clearErrorEtSecondLastName()
        clearErrorDpBirthDate() // TODO FALTA IMPLEMENTAR
        clearErrorEtPassword()
        clearErrorEtPasswordRepeat()
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

    private fun clearErrorEtSecondLastName() {
        binding.etSecondLastName.setHintTextColor(defaultHintColor)
        binding.etSecondLastName.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorDpBirthDate(){
        //TODO SIN IMPLEMENTAR
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