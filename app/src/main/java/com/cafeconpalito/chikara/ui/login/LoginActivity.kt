package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.domain.utils
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import com.cafeconpalito.chikara.utils.CypherTextToMD5
import dagger.hilt.android.AndroidEntryPoint

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
            val passwordAuthentication = CypherTextToMD5(passwordInput)

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
}