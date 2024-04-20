package com.cafeconpalito.chikara.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityRegisterBinding
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.ValidateFields
import com.cafeconpalito.chikara.utils.dataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    //@Inject constructor(private val registerValidateFields: RegisterValidateFields)

    //TODO: CAMBIAR EL BIRD DATE DE UN EDIT TEXT A UN DATE PICKER!

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var validateFields: ValidateFields

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
                genericClearErrorEt(binding.etUserName)
            } else {
                //TODO AÑADIR VALIDAR
                //TODO AÑADIR BUSCAR SI EXISTE EN DB
            }
        }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etUserName)

    }

    private fun etEmailListeners() {

        //Cuando Gana Foco
        binding.etEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etEmail)
            } else {
                //TODO AÑADIR VALIDAR
                //TODO AÑADIR BUSCAR SI EXISTE EN DB
            }
        }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etEmail)

    }


    private fun etFirstNamelListeners() {

        //Cuando Gana Foco
        binding.etFirstName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etFirstName)
            } else {
                //TODO AÑADIR VALIDAR
            }
        }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etFirstName)

    }

    private fun etFirstLastNameListeners() {

        //Cuando Gana Foco
        binding.etFirstLastName.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    genericClearErrorEt(binding.etFirstLastName)
                } else {
                    //TODO AÑADIR VALIDAR
                }
            }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etFirstLastName)

    }

    private fun etSecondLastNameListeners() {

        //Cuando Gana Foco
        binding.etSecondLastName.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    genericClearErrorEt(binding.etSecondLastName)
                } else {
                    //TODO AÑADIR VALIDAR
                }
            }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etSecondLastName)

    }

    private fun dpBithDateListener() {
        //TODO("Not yet implemented")
    }

    private fun etPasswordListeners() {

        //Cuando Gana Foco
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etPassword)
            } else {
                //TODO AÑADIR QUE AL SALIR BUSQUE SI EL PASSWORD ES VALIDO
            }
        }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etPassword)

    }

    private fun etPasswordRepeatListeners() {

        //Cuando Gana Foco
        binding.etPasswordRepeat.onFocusChangeListener =
            View.OnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    genericClearErrorEt(binding.etPasswordRepeat)
                } else {
                    //TODO AÑADIR QUE AL SALIR BUSQUE SI AMBOS PASS COINCIDEN
                }
            }
        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etPasswordRepeat)

    }

    /**
     * Funcion Generica para inicializar al cambiar el cambio del texto de un EditText limpie los errores
     */
    private fun genericOnModifyTextListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                genericClearErrorEt(editText)
            } // Al modificar el texto
        })
    }


    private fun tryToRegister() {

        //Limpio los errores al dar al boton de registro.
        clearErrors()

    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * Clear All Errors
     */
    private fun validateRegisterFields() {
        validateEtUserName()
        validateEtEmail()
        validateEtFirstName()
        validateEtFirstLastName()
        validateEtSecondLastName()
        validateDpBirthDate() // TODO FALTA IMPLEMENTAR
        validateEtPassword()
        validateEtPasswordRepeat()
    }


    /**
     * Validate if EditText Is Blank or Have Empty Spaces Y Pinta los errores
     * Return True if is OK
     */
    private fun genericValidateEditText(editText: EditText):Boolean {
        val textToValidate = editText.text.toString()
        if (validateFields.validateHaveBlankSpaces(textToValidate)) {
            val hintText = editText.hint// Obtiene el texto del Hint y lanso un toast
            Toast.makeText(
                this,
                "El campo $hintText no puede contener espacios en blanco",
                Toast.LENGTH_LONG
            ).show()
            editText.setTextColor(errorEditTextColor)
            return false
        } else if (binding.etUserName.text.isBlank()) {
            editText.setHintTextColor(errorHintColor)
            return false
        }
        return true
    }

    /**
     * Comprobar si el campo User Name es correcto
     * True si es correcto;
     */
    private fun validateEtUserName():Boolean {

        if (!genericValidateEditText(binding.etUserName)){
            return false
        } else if (!validateFields.validUserName(binding.etUserName.text.toString())) {
            //TODO: Añadir TOAST de que el usuario no puede tener menos de tres caracteres o no puede contener @
            return false
        }
        //TODO faltan cosas de comprobar!


        return true
    }

    private fun validateEtEmail() {
        //TODO("Not yet implemented")
    }

    private fun validateEtFirstName() {
        //TODO("Not yet implemented")
    }

    private fun validateEtFirstLastName() {
        //TODO("Not yet implemented")
    }

    private fun validateEtSecondLastName() {
        //TODO("Not yet implemented")
    }

    private fun validateDpBirthDate() {
        //TODO("Not yet implemented")
    }

    private fun validateEtPassword() {
        //TODO("Not yet implemented")
    }

    private fun validateEtPasswordRepeat() {
        //TODO("Not yet implemented")
    }


    /**
     * Clear All Errors
     */
    private fun clearErrors() {
        genericClearErrorEt(binding.etUserName)
        genericClearErrorEt(binding.etEmail)
        genericClearErrorEt(binding.etFirstName)
        genericClearErrorEt(binding.etFirstLastName)
        genericClearErrorEt(binding.etSecondLastName)
        genericClearErrorEt(binding.etPassword)
        genericClearErrorEt(binding.etPasswordRepeat)

        clearErrorDpBirthDate() // TODO FALTA IMPLEMENTAR

    }

    /**
     * Generic clear errors of Edit Text
     */
    private fun genericClearErrorEt(editText: EditText) {
        editText.setHintTextColor(defaultHintColor)
        editText.setTextColor(defaultEditTextColor)
    }

    private fun clearErrorDpBirthDate() {
        //TODO SIN IMPLEMENTAR
    }


}