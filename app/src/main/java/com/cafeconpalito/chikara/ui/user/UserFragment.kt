package com.cafeconpalito.chikara.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentUserBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.login.LoginActivity
import com.cafeconpalito.chikara.ui.utils.isKeyboardVisible
import com.cafeconpalito.chikara.utils.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    lateinit var userPreferences: UserPreferences

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
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogOut.setOnClickListener {
            launchLogOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        //setupKeyboardListener(binding.root)
    }

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
            userPreferences.deltePreference(UserPreferences.KEY_USER_STR)
            userPreferences.deltePreference(UserPreferences.KEY_PASSWORD_STR)
        }
    }

}


