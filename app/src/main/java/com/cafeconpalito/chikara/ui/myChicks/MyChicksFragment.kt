package com.cafeconpalito.chikara.ui.myChicks


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafeconpalito.chikara.databinding.FragmentMyChicksBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    private val mcViewModel: MyChicksViewModel by viewModels()

    private lateinit var mutableListChicks: MutableList<ChickDto>

    private lateinit var adapter: MyChicksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyChicksBinding.inflate(inflater, container, false)
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

    private fun initRecyclerView() {
        val method = object {}.javaClass.enclosingMethod?.name
        Log.d(this.javaClass.simpleName, "Method: $method -> start")
        //Log.d("FindChicks", " UserChickFragment -> initRecyclerView()")

        CoroutineScope(Dispatchers.IO).launch {

            mcViewModel.getUserChicks()

            withContext(Dispatchers.Main) {
                mcViewModel.userChicksLiveData.observe(viewLifecycleOwner) { listMyChicks ->
                    with(binding.rvFindChick) {
                        //Selecciono el tipo de Layout para el RV
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        mutableListChicks.addAll(listMyChicks)
                        //Paso la nueva lista de datos!
                        //TODO MIRAR SI FUNCIONA
                        adapter = MyChicksAdapter(mutableListChicks) { deleteChick(it) }

                    }
                }

                //Mientras no carge estara sin ser visible...
                mcViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    Log.d(this.javaClass.simpleName, "Method: $method -> loading")
                    //Log.d("UserChicks", " UserChickFragment -> isLoagind!")
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

}