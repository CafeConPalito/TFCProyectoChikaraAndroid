package com.cafeconpalito.chikara.ui.findChicks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafeconpalito.chikara.databinding.FragmentFindChicksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class FindChicksFragment : Fragment() {
    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentFindChicksBinding? = null
    private val binding get() = _binding!!

    private val fcViewModel: FindChicksViewModel by viewModels()

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

        Log.d( this.javaClass.simpleName , "Method: ${this.javaClass.enclosingMethod?.name} -> start")

        CoroutineScope(Dispatchers.IO).launch {

            fcViewModel.getTopChicks()

            withContext(Dispatchers.Main) {
                fcViewModel.topChicksLiveData.observe(viewLifecycleOwner) { ListTopChicks ->
                    with(binding.rvFindChick) {
                        //Selecciono el tipo de Layout para el RV
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        //Paso la nueva lista de datos!
                        adapter = FindChicksAdapter(ListTopChicks) {
                        }
                    }
                }

                //Mientras no carge estara sin ser visible...
                fcViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    Log.d( this.javaClass.simpleName , "Method: ${this.javaClass.enclosingMethod?.name} -> isLoading")
                    //TODO POR SI QUEREMOS AÑADIR UN PROGRESS BAR
                }
            }
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