package com.cafeconpalito.chikara.ui.findChicks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentFindChicksBinding
import com.cafeconpalito.chikara.databinding.FragmentUserBinding
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject


class FindChicksFragment : Fragment() {
    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentFindChicksBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var chickUseCases: ChickUseCases


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindChicksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        lifecycleScope.launch {
            val listTopChicks = chickUseCases.getTopChicks()

            for (x in listTopChicks) {
                Log.i("Chick", x.toString())
            }
        }

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