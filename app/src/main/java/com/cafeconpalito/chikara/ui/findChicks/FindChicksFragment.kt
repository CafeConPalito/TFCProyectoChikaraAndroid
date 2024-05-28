package com.cafeconpalito.chikara.ui.findChicks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafeconpalito.chikara.databinding.FragmentFindChicksBinding
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.newChick.ElementChickAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FindChicksFragment : Fragment() {
    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentFindChicksBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FindChicksAdapter

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

            //TODO EL RV ESTA SIN TESTEAR.
            val listTopChicks = chickUseCases.getTopChicks()

//            for (x in listTopChicks) {
//                Log.i("Chick", x.toString())
//            }

            //El estilo del la lista de Objetos para mostrar (lista vertical normalita)
            binding.rvFindChick.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            //Le paso la lista al adaptador inicialmente.
            adapter = FindChicksAdapter(listTopChicks){ }

            //le paso el adaptador a el Recycler View
            binding.rvFindChick.adapter = adapter

        }

    }

    private fun initListeners() {

        //EDIT TEXT AL DARLE BUSCAR
        binding.etFindChick.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    //TODO AL DARLE BUSCAR LLAMAR A LA API Y ACTUALIZAR EL RV
                    true
                }

                else -> false
            }
        }



    }
}