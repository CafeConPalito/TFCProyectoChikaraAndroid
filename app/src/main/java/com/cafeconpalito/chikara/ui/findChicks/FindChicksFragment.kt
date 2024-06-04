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
import com.cafeconpalito.chikara.domain.model.ChickDto
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

    private lateinit var mutableListChicks: MutableList<ChickDto>

    private lateinit var adapter: FindChicksAdapter

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
        val method = object {}.javaClass.enclosingMethod?.name
        Log.d(this.javaClass.simpleName, "Method: $method -> start")

        CoroutineScope(Dispatchers.IO).launch {

            fcViewModel.getTopChicks()

            withContext(Dispatchers.Main) {
                fcViewModel.topChicksLiveData.observe(viewLifecycleOwner) { listTopChicks ->
                    with(binding.rvFindChick) {
                        //Selecciono el tipo de Layout para el RV
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        mutableListChicks.addAll(listTopChicks)
                        //Paso la nueva lista de datos!
                        //TODO MIRAR SI FUNCIONA
                        adapter = FindChicksAdapter(mutableListChicks) { deleteChick(it) }
                    }
                }

                //Mientras no carge estara sin ser visible...
                fcViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    Log.d(this.javaClass.simpleName, "Method: $method -> isLoading")
                    //TODO POR SI QUEREMOS AÑADIR UN PROGRESS BAR
                }
            }
        }
    }

    /**
     * Delete the chick from the list
     *
     */
    private fun deleteChick(position: Int) {

        //TODO DECIDIR SI SE BORRA AQUI O SE BORRA EN EL VIEW HOLDER
        //Elimino el elemento de la lista
        mutableListChicks.removeAt(position)
        //Notifico que se ha borrado
        adapter.notifyItemRemoved(position)

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