package com.cafeconpalito.chikara.ui.myChicks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentMyChicksBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MyChicksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MyChicksFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentMyChicksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyChicksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Le paso el contecto a User Preferences.

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {

        //EDIT TEXT AL DARLE BUSCAR
        binding.etFindChick.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    //TODO AL DARLE BUSCAR LLAMAR A LA API
                    true
                }
                else -> false
            }
        }
    }


}