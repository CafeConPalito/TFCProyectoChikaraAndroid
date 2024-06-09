package com.cafeconpalito.chikara.ui.myChicks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentMyChicksBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class MyChicksFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentMyChicksBinding? = null
    private val binding get() = _binding!!

    private val mcViewModel: MyChicksViewModel by viewModels()

    @Inject
    lateinit var chickUseCase: ChickUseCases

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

        CoroutineScope(Dispatchers.IO).launch {

            mcViewModel.getUserChicks()

            withContext(Dispatchers.Main) {
                mcViewModel.userChicksLiveData.observe(viewLifecycleOwner) { listMyChicks ->
                    with(binding.rvFindChick) {
                        //Selecciono el tipo de Layout para el RV
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        //mutableListChicks = listMyChicks.toMutableList()
                        //Paso la nueva lista de datos!
                        //TODO MIRAR SI FUNCIONA
                        adapter = MyChicksAdapter(listMyChicks) { deleteChick(it, adapter, listMyChicks) }

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
    private fun deleteChick(
        position: Int,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?,
        listMyChicks: MutableList<ChickDto>
    ) {

        //Lanzar la pregunta al Usuario.
        //if -> ok
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(R.string.AlertDialogTittle)
            .setMessage(R.string.AlertDialogMessage)
            .setPositiveButton(R.string.AlertDialogAccept) { dialog, which ->

                dialog.dismiss()

                val chickId = listMyChicks.get(position)._id

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            //BORRA EL CHICK DE LA DB
                            chickUseCase.deleteChick(chickId)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                //Elimino el elemento de la lista
                listMyChicks.removeAt(position)
                //Notifico que se ha borrado
                adapter!!.notifyItemRemoved(position)

            }
            .setNegativeButton(R.string.AlertDialogCancel) { dialog, which ->
                dialog.dismiss() // Cierra el diálogo si el usuario cancela
            }
            .show()
    }

}