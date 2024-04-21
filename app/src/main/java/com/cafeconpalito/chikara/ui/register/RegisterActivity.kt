package com.cafeconpalito.chikara.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityRegisterBinding
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import com.cafeconpalito.chikara.domain.useCase.RegisterUseCase
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.ValidateFields
import com.cafeconpalito.chikara.utils.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity  : AppCompatActivity() {

    //@Inject constructor(private val registerValidateFields: RegisterValidateFields)

    //TODO: CAMBIAR EL BIRD DATE DE UN EDIT TEXT A UN DATE PICKER!

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var validateFields: ValidateFields
    @Inject
    lateinit var registerUseCase: RegisterUseCase

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
                validateEtUserName()
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
                validateEtEmail()
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
                validateEtFirstName()
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
                    validateEtFirstLastName()
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
                    validateEtSecondLastName()
                }
            }

        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etSecondLastName)

    }

    private fun dpBithDateListener() {
        //TODO("Not yet i+mplemented")
    }

    private fun etPasswordListeners() {

        //Cuando Gana Foco
        binding.etPassword.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etPassword)
            } else {
                validateEtPassword()
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
                    validateEtPasswordRepeat()
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
     * Comprueba si el campo User Name es correcto
     * Tiene el formato correcto, El usuario existe previamente
     * True si es correcto;
     */
    private fun validateEtUserName(): Boolean {

        if (validateEtUserNameIsValid()){
            validateEtUserNameExist {
                if (it){ // El usuario Existe
                    Toast.makeText(
                        this,
                        "El User Name ya existe",
                        Toast.LENGTH_LONG
                    ).show()
                    genericIsErrorEt(binding.etUserName)
                }
                return@validateEtUserNameExist
            }
        }
        return false
    }

    /**
     * Comprueba que el User Name es de formato valido.
     * @return Boolean True si es valido
     */
    private fun validateEtUserNameIsValid(): Boolean {

        if (!genericValidateEditText(binding.etUserName)) {
            return false
        } else if (!validateFields.validUserName(binding.etUserName.text.toString())) {
            Toast.makeText(
                this,
                "El campo ${binding.etUserName.hint} tiene que tener al menos tres caracteres y no puede contener @",
                Toast.LENGTH_LONG
            ).show()
            genericIsErrorEt(binding.etUserName) // Pinta los errores.
            return false
        }
        return true
    }

    /**
     * Comprueba si el User Name Existe (Consulta a la API)
     * @return Boolean True Si existe
     */
    private fun validateEtUserNameExist(callback: (Boolean) -> Unit) {
        lifecycleScope.launch() {
            //Completa con el @
            var userName = validateFields.completeUserName(binding.etUserName.text.toString())
            if (registerUseCase.userNameExist(userName)) {
                Log.i("RegistroUsuario: ", "REGISTER Usuario Existe SI! EXISTE")
                callback(true)
            }else {
                Log.i("RegistroUsuario: ", "REGISTER Usuario Existe NO! EXISTE")
                callback(false)
            }
        }
    }
    
    private fun validateEtEmail() {
        //TODO("Not yet implemented")
    }

    /**
     * Comprueba Que el First Name no este en blanco si lo esta pinta el error
     * True si el campo es valido
     */
    private fun validateEtFirstName(): Boolean {
        if (binding.etFirstName.text.isBlank()) {
            genericIsErrorEt(binding.etFirstName)
            return false
        }
        return true
    }


    /**
     * Comprueba Que el First Last Name no este en blanco si lo esta pinta el error
     * True si el campo es valido
     */
    private fun validateEtFirstLastName(): Boolean {
        if (binding.etFirstLastName.text.isBlank()) {
            genericIsErrorEt(binding.etFirstLastName)
            return false
        }
        return true
    }

    /**
     * Comprueba Que el Second Last Name no este en blanco. NO PINTA NUNCA ERROR
     * True si el campo es valido
     */
    private fun validateEtSecondLastName(): Boolean {
        if (binding.etFirstLastName.text.isBlank()) {
            return false
        }
        return true
    }

    private fun validateDpBirthDate() {
        //TODO("Not yet implemented")
    }

    /**
     * Comprueba el campo del password
     */
    private fun validateEtPassword(): Boolean {
        if (!genericValidateEditText(binding.etPassword)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePassword(binding.etPassword.text.toString())) { // Valida que el Password formato correcto
            Toast.makeText(
                this,
                "El Password debe contener al menos ocho caracteres una mayuscula una minuscula y un numero",
                Toast.LENGTH_LONG
            ).show()
            genericIsErrorEt(binding.etPassword)
            return false
        }
        return true

    }

    private fun validateEtPasswordRepeat(): Boolean {
        if (!genericValidateEditText(binding.etPasswordRepeat)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePasswordsMarches(
                binding.etPassword.text.toString(),
                binding.etPasswordRepeat.text.toString()
            )
        ) {
            Toast.makeText(
                this,
                "Los Passwords no coinciden",
                Toast.LENGTH_LONG
            ).show()
            genericIsErrorEt(binding.etPasswordRepeat)
            return false
        }
        return true
    }


    /**
     * Validate if EditText Is Blank or Have Empty Spaces Y Pinta los errores
     * Return True if is OK
     */
    private fun genericValidateEditText(editText: EditText): Boolean {
        val textToValidate = editText.text.toString()
        if (validateFields.validateHaveBlankSpaces(textToValidate)) {
            val hintText = editText.hint// Obtiene el texto del Hint y lanso un toast
            Toast.makeText(
                this,
                "El campo $hintText no puede contener espacios en blanco",
                Toast.LENGTH_LONG
            ).show()
            genericIsErrorEt(editText)//Pinta el error
            return false
        } else if (binding.etUserName.text.isBlank()) {
            genericIsErrorEt(editText)//Pinta el error
            return false
        }
        return true
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

    /**
     * Generic is errors of Edit Text
     * Pinta los campos en rojo para los EditText
     */
    private fun genericIsErrorEt(editText: EditText) {
        editText.setHintTextColor(errorHintColor)
        editText.setTextColor(errorEditTextColor)
    }

    private fun clearErrorDpBirthDate() {
        //TODO SIN IMPLEMENTAR
    }


}