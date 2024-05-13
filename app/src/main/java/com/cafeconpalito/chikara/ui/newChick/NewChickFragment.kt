package com.cafeconpalito.chikara.ui.newChick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentNewChickBinding

class NewChickFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentNewChickBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewChickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        // TODO("Not yet implemented")
    }

}