package com.cafeconpalito.chikara.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ActivityLoginBinding
import com.cafeconpalito.chikara.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
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
        if (binding.etUserName.text.isBlank()){
            //binding.etUserName.setHintTextColor(R.color.error_text)
        }
        if (binding.etPassword.text.isBlank()){
            //binding.etPassword.setHintTextColor(R.color)
        }

    }
}