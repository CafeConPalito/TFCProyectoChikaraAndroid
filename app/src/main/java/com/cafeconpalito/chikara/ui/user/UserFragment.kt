package com.cafeconpalito.chikara.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentUserBinding
import com.cafeconpalito.chikara.ui.home.HomeActivity
import com.cafeconpalito.chikara.ui.utils.isKeyboardVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        //setupKeyboardListener(view)
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        // TODO("Not yet implemented")
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


}