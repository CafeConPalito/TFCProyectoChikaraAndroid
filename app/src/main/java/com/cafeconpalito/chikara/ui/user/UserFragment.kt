package com.cafeconpalito.chikara.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentUserBinding
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.useCase.UserUseCase
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.ui.utils.GenericToast
import com.cafeconpalito.chikara.ui.utils.isKeyboardVisible
import com.cafeconpalito.chikara.utils.UserPreferences
import com.cafeconpalito.chikara.utils.ValidateFields
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var validateFields: ValidateFields

    @Inject
    lateinit var userUseCase: UserUseCase

    private var userInformation: UserDto? = null

    //LLevar el metodo initColor()
    private var defaultEditTextColor = 0
    private var errorEditTextColor = 0
    private var defaultHintColor = 0
    private var errorHintColor = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())

        defaultEditTextColor = ContextCompat.getColor(requireContext(), R.color.default_edit_text)
        errorEditTextColor = ContextCompat.getColor(requireContext(), R.color.error_edit_text)
        defaultHintColor = ContextCompat.getColor(requireContext(), R.color.default_hint_edit_text)
        errorHintColor = ContextCompat.getColor(requireContext(), R.color.error_hint_edit_text)

        initUI()
    }

    private fun initUI() {
        loadInformation()
        initListeners()
    }

    private fun loadInformation() {
        CoroutineScope(Dispatchers.IO).launch {
            userInformation = userUseCase.getUserInformation()
            withContext(Dispatchers.Main) {
                if (userInformation != null) {
                    //Set UserName
                    binding.tvUsername.text = userInformation!!.user_name
                    binding.etNewUserName.setText(userInformation!!.user_name)
                    //Set Email
                    binding.etNewEmail.setText(userInformation!!.email)
                } else {
                    //TODO SI LA RESPUESTA ES NULL MOSTRAR UN MENSAJE DE ERROR!
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnUpdateInfo.setOnClickListener { updateInformation() }
        binding.btnLogOut.setOnClickListener {
            launchLogOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        etUserNameListeners()
        etEmailListeners()
        //setupKeyboardListener(binding.root)
    }

    /**
     * Update User Information
     */
    private fun updateInformation() {
        //TODO REVISAR COMO UPDATEAR EL PASS O LA INFO DEL USUARIO,
        // SI LOS PASS ESTAN VACIOS NO ES NECESARIO MODIFICAR LA CONTRASEÑA.
        // SI LOS CAMPOS DE EMAIL AND USERNAME ESTAN IGUALES NO HACE NADA.
        if (validateFields()) {//PENSANDO QUE LA VALIDACION ES CORRECTA!

            userInformation!!.user_name = binding.etNewUserName.text.toString()
            userInformation!!.email = binding.etNewEmail.text.toString()
            userInformation!!.pwd = binding.etNewPassword.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val updateInfo = userUseCase.updateUserInformation(userInformation!!)
                withContext(Dispatchers.Main) {
                    //SI EL UPDATE ES CORRECTO!

                }
            }

        }
    }

    private fun validateFields(): Boolean {

        return !validateEtUserName() and //Es Necesario Negarlo para que si es falso se que no esta utilizado
                !validateEtEmail() and //Es Necesario Negarlo para que si es falso se que no esta utilizado
                validateEtPassword() and
                validateEtPasswordRepeat()

    }


    private fun etUserNameListeners() {
        //Cuando Gana Foco
        binding.etNewUserName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etNewUserName)
            } else {
                validateEtUserName()
            }
        }
        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etNewUserName)
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
                    GenericToast.generateToast(
                        requireContext(),
                        getString(R.string.ToastUserAlreadyExists),
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                    genericIsErrorEt(binding.etNewUserName)
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

        if (!genericValidateEditText(binding.etNewUserName)) {
            return false
        } else if (!validateFields.validUserName(binding.etNewUserName.text.toString())) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.ToastUserFormatIncorrect),
                Toast.LENGTH_LONG,
                true
            ).show()
            genericIsErrorEt(binding.etNewUserName) // Pinta los errores.
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
            val userName = validateFields.completeUserName(binding.etNewUserName.text.toString())
            if (userUseCase.userNameExist(userName)) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    private fun etEmailListeners() {
        //Cuando Gana Foco
        binding.etNewEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                genericClearErrorEt(binding.etNewEmail)
            } else {
                validateEtEmail()
            }
        }
        //Cuando el texto se modifica
        genericOnModifyTextListener(binding.etNewEmail)
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
                    GenericToast.generateToast(
                        requireContext(),
                        getString(R.string.ToastEmailAlreadyExists),
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                    genericIsErrorEt(binding.etNewEmail)
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

        if (!genericValidateEditText(binding.etNewEmail)) {
            return false
        } else if (!validateFields.validEmail(binding.etNewEmail.text.toString())) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.ToastEmailFormatIncorrect),
                Toast.LENGTH_LONG,
                true
            ).show()
            genericIsErrorEt(binding.etNewEmail) // Pinta los errores.
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
            if (userUseCase.emailExist(binding.etNewEmail.text.toString())) {
                callback(true)
            } else {
                callback(false)
            }
        }
    }

    /**
     * Comprueba el campo del password
     */
    private fun validateEtPassword(): Boolean {
        if (!genericValidateEditText(binding.etNewPassword)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePassword(binding.etNewPassword.text.toString())) { // Valida que el Password formato correcto
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.ToastPasswordFormatIncorrect),
                Toast.LENGTH_LONG,
                true
            ).show()
            genericIsErrorEt(binding.etNewPassword)
            return false
        }
        return true

    }

    /**
     * Valida que los passwords coinciden.
     */
    private fun validateEtPasswordRepeat(): Boolean {
        if (!genericValidateEditText(binding.etNewPasswordRepeat)) { //Valida que no este en blanco o contenga espacios y pinta el Error.
            return false
        } else if (!validateFields.validatePasswordsMatches(
                binding.etNewPassword.text.toString(),
                binding.etNewPasswordRepeat.text.toString()
            )
        ) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.ToastPasswordMissMatch),
                Toast.LENGTH_LONG,
                true
            ).show()
            genericIsErrorEt(binding.etNewPasswordRepeat)
            return false
        }
        return true
    }


    //TODO PARA BORRAR KEYBOARD
    private fun setupKeyboardListener(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val isKeyboardVisible = view.isKeyboardVisible()
            Log.d("keyboard", "Keyboard visible: $isKeyboardVisible")
            (activity as? HomeActivity)?.showNavBar(!isKeyboardVisible)
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchLogOut() {
        CoroutineScope(Dispatchers.IO).launch {
            userPreferences.deleteAllUserPreferences()
        }
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

    /**
     * Validate if EditText Is Blank or Have Empty Spaces Y Pinta los errores
     * Return True if is OK
     */
    private fun genericValidateEditText(editText: EditText): Boolean {
        val textToValidate = editText.text.toString()
        if (validateFields.validateHaveBlankSpaces(textToValidate)) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.ToastFieldWithOutBlankSpaces),
                Toast.LENGTH_LONG,
                true
            ).show()
            genericIsErrorEt(editText)//Pinta el error
            return false
        } else if (editText.text.isBlank()) {
            genericIsErrorEt(editText)//Pinta el error
            return false
        }
        return true
    }
}


