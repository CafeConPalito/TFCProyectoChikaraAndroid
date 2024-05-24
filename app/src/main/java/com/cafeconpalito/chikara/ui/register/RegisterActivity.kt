package com.cafeconpalito.chikara.ui.register

import android.app.DatePickerDialog
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
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityRegisterBinding
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.useCase.RegisterUseCase
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import com.cafeconpalito.chikara.ui.utils.GenericToast
import com.cafeconpalito.chikara.utils.UserPreferences
import com.cafeconpalito.chikara.utils.ValidateFields
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

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
        dpBirthDateListener()
        etPasswordListeners()
        etPasswordRepeatListeners()

    }

    /**
     * Accion del boton Login, cambia a la vista LoginActivity
     */
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    /**
     * Accion del boton Register, comprueba que todos los campos son correctos, si es asi intenta registrar.
     */
    private fun tryToRegister() {

        clearErrors() //Limpia los errores al dar al boton de registro.
        binding.pbRegister.isVisible = true
        if (validateRegisterFields()) { // Comprueba que todos los campos son correctos.

            Log.i("RegistroUsuario: ", "Todos los campos correctos intento registrar!")
            lifecycleScope.launch() {
                if(registerUseCase.registerUser(makeUserDto())){ //Si el registro es satisfactorio
                    registerSatisfactoryGoToLogin()
                }else { //Si no lo es.
                    GenericToast.generateToast(applicationContext,getString(R.string.ToastGenericFail), Toast.LENGTH_LONG, true).show()
                }
            }
        } else {
            Log.i("RegistroUsuario: ", "alguno de los campos es incorrecto")
        }
        binding.pbRegister.isVisible = false

    }

    private fun registerSatisfactoryGoToLogin(){
//        var bundle:Bundle = Bundle()
//        bundle.putString(UserPreferences.KEY_USER_STR.name, "@"+binding.etUserName.text.toString())
//        bundle.putString(UserPreferences.KEY_PASSWORD_STR.name, binding.etPassword.text.toString())

        val intent = Intent(this, LoginActivity::class.java).apply {
            putExtra(UserPreferences.KEY_USER_STR.name, "@"+binding.etUserName.text.toString())
            putExtra(UserPreferences.KEY_PASSWORD_STR.name, binding.etPassword.text.toString())
        }
        startActivity(intent)
    }

    /**
     * Genera un UserDto con los datos del Registro.
     */
    private fun makeUserDto(): UserDto {

        val cypherTextToMD5 = CypherTextToMD5()

        //METODO CORRETO, FALTA LA FECHA
        val userDto = UserDto(

            user_name = "@"+binding.etUserName.text.toString(),
            email = binding.etEmail.text.toString(),
            pwd = cypherTextToMD5(binding.etPassword.text.toString()),
            first_name = binding.etFirstName.text.toString().trim().replace(Regex("\\s+"), " "),
            first_last_name = binding.etFirstLastName.text.toString().trim().replace(Regex("\\s+"), " "),
            second_last_name = binding.etSecondLastName.text.toString().trim().replace(Regex("\\s+"), " "), // Puedes asignar null si es opcional
            birthdate = binding.etBirthDate.text.toString()

        )

        return userDto

    }


    //LISTENERS

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



    private fun dpBirthDateListener() {

        binding.etBirthDate.setOnClickListener {

            val calendar = Calendar.getInstance()
            val brithDate = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                updateBirthDate(calendar)

            }

            DatePickerDialog(
                this,
                brithDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        genericOnModifyTextListener(binding.etBirthDate)

    }

    private fun updateBirthDate(calendar: Calendar) {
        val dateFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        binding.etBirthDate.setText (simpleDateFormat.format(calendar.time))
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


    //VALIDATION MANAGERS

    /**
     * valida todos los campos.
     * return True si todos son correctos.
     */
    private fun validateRegisterFields(): Boolean {

        return !validateEtUserName() and //Es Necesario Negarlo para que si es falso se que no esta utilizado
                !validateEtEmail() and //Es Necesario Negarlo para que si es falso se que no esta utilizado
                validateEtFirstName() and
                validateEtFirstLastName() and
                validateEtSecondLastName() and
                validateDpBirthDate() and
                validateEtPassword() and
                validateEtPasswordRepeat()

    }

    /**
     * Comprueba si el campo User Name es correcto
     * Tiene el formato correcto, El usuario existe previamente
     * True si es correcto;
     */
    private fun validateEtUserName(): Boolean {

        if (validateEtUserNameIsValid()) {
            validateEtUserNameExist {
                if (it) { // El usuario Existe
                    GenericToast.generateToast(this,getString(R.string.ToastUserAlreadyExists), Toast.LENGTH_LONG, true).show()
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
            GenericToast.generateToast(this,getString(R.string.ToastUserFormatIncorrect), Toast.LENGTH_LONG, true).show()
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
            val userName = validateFields.completeUserName(binding.etUserName.text.toString())
            if (registerUseCase.userNameExist(userName)) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    /**
     * Comprueba si el campo Email es correcto
     * Tiene el formato correcto, El Email existe previamente
     * True si es correcto;
     */
    private fun validateEtEmail(): Boolean {
        if (validateEtEmailIsValid()) {
            validateEtEmailExist {
                if (it) { // El Email ya esta registrado
                    GenericToast.generateToast(this,getString(R.string.ToastEmailAlreadyExists), Toast.LENGTH_LONG, true).show()
                    genericIsErrorEt(binding.etEmail)
                }
                return@validateEtEmailExist
            }
        }
        return false
    }

    /**
     * Comprueba que el Email es de formato valido.
     * @return Boolean True si es valido
     */
    private fun validateEtEmailIsValid(): Boolean {

        if (!genericValidateEditText(binding.etEmail)) {
            return false
        } else if (!validateFields.validEmail(binding.etEmail.text.toString())) {
            GenericToast.generateToast(this,getString(R.string.ToastEmailFormatIncorrect), Toast.LENGTH_LONG, true).show()
            genericIsErrorEt(binding.etEmail) // Pinta los errores.
            return false
        }
        return true
    }

    /**
     * Comprueba si el Email Existe (Consulta a la API)
     * @return Boolean True Si existe
     */
    private fun validateEtEmailExist(callback: (Boolean) -> Unit) {
        lifecycleScope.launch() {
            if (registerUseCase.emailExist(binding.etEmail.text.toString())) {
                callback(true)
            } else {
                callback(false)
            }
        }
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
     * Comprueba que el First Last Name no este en blanco si lo esta pinta el error
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

    /**
     * Comprueba que el Birth Date no este en blanco si lo esta pinta el error
     * True si el campo es valido
     */
    private fun validateDpBirthDate(): Boolean {
        if (binding.etBirthDate.text.isBlank()){
            genericIsErrorEt(binding.etBirthDate)
            return false
        }
        return true
    }

    /**
     * Comprueba el campo del password
     */
    private fun validateEtPassword(): Boolean {
        if (!genericValidateEditText(binding.etPassword)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePassword(binding.etPassword.text.toString())) { // Valida que el Password formato correcto
            GenericToast.generateToast(this,getString(R.string.ToastPasswordFormatIncorrect), Toast.LENGTH_LONG, true).show()
            genericIsErrorEt(binding.etPassword)
            return false
        }
        return true

    }

    /**
     * Valida que los passwords coinciden.
     */
    private fun validateEtPasswordRepeat(): Boolean {
        if (!genericValidateEditText(binding.etPasswordRepeat)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePasswordsMatches(
                binding.etPassword.text.toString(),
                binding.etPasswordRepeat.text.toString()
            )
        ) {
            GenericToast.generateToast(this,getString(R.string.ToastPasswordMissMatch), Toast.LENGTH_LONG, true).show()
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
            GenericToast.generateToast(this,getString(R.string.ToastFieldWithOutBlankSpaces), Toast.LENGTH_LONG, true).show()
            genericIsErrorEt(editText)//Pinta el error
            return false
        } else if (binding.etUserName.text.isBlank()) {
            genericIsErrorEt(editText)//Pinta el error
            return false
        }
        return true
    }


    //ERROR MANAGERS

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
        genericClearErrorEt(binding.etBirthDate)
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

}