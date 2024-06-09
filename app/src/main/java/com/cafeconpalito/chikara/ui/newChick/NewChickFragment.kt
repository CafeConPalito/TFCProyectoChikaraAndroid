package com.cafeconpalito.chikara.ui.newChick

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.FragmentNewChickBinding
import com.cafeconpalito.chikara.domain.assembler.ChickDtoAssembler
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.utils.DotIndicatorDecoration
import com.cafeconpalito.chikara.ui.utils.GenericToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NewChickFragment : Fragment() {

    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentNewChickBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ElementChickAdapter

    //Lista de elementos para el post
    private var contentElements = mutableListOf<Uri>()

    @Inject
    lateinit var chickUseCases: ChickUseCases

    //Launcher encargado de obtener la Uri de la imagen seleccinada
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->

        //Si la imagen fue seleccionada
        if (uri != null) {

            Log.d("PhotoPicker", "Selected URI: $uri")
            //selectedImageUri = uri
            //Si Selecciona una imagen envio la Uri Sino nada ;)
            addElement(uri)

        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

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
        initRecyclerView()
    }

    private fun initListeners() {
        binding.btnPublish.setOnClickListener {
            publishNewChick()
        }

        binding.btnAddElement.setOnClickListener {
            launchImagePicker()
        }
    }

    /**
     * Inicializa el Recicler View
     */
    private fun initRecyclerView() {

        //El estilo del la lista de Objetos para mostrar (lista vertical normalita)
        binding.rvChickElements.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        //Añado decoracion al RecyclerView
        binding.rvChickElements.addItemDecoration(DotIndicatorDecoration(requireContext()))

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvChickElements)

        //Le paso la lista al adaptador inicialmente.
        // Con lambda
        adapter = ElementChickAdapter(contentElements) { deleteElement(it) }

        //le paso el adaptador a el Recycler View
        binding.rvChickElements.adapter = adapter

        //TODO PROBAR DRAG AND DROP!
        val callback = DragDropItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvChickElements)

    }

    /**
     * Metodo que añade tareas a la lista, llamado desde el listener del Boton!
     */
    private fun addElement(uri: Uri) {

        val newElement = uri
        contentElements.add(newElement)
        adapter.notifyDataSetChanged()

    }

    /**
     * Borra la tarea de la lista y actualiza la lista.
     */
    private fun deleteElement(position: Int) {

        contentElements.removeAt(position)
        //Correcto aviso de que borro algo
        adapter.notifyItemRemoved(position)
        //Metodo antiguo que revisa toda la lista

    }


    //TODO PONER BONITO
    /**
     * Action of Button publish, try to publish NewChick validate fields
     */
    private fun publishNewChick() {
        val method = object {}.javaClass.enclosingMethod?.name

        if (contentElements.isEmpty()) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.add_title_chick),
                Toast.LENGTH_LONG,
                true
            ).show()

        } else if (binding.etTitle.text.isBlank()) {
            GenericToast.generateToast(
                requireContext(),
                getString(R.string.add_elements_to_chick),
                Toast.LENGTH_LONG,
                true
            ).show()
        } else {

            val chickDtoAssembler = ChickDtoAssembler()

            val title = binding.etTitle.text.toString()
            val isPrivate = binding.sIsPrivate.isChecked
            val newChick = chickDtoAssembler.buildDto(title, isPrivate, contentElements)

            CoroutineScope(Dispatchers.Main).launch {
                //Envio el contexto de la Aplicacion!
                val result =
                    chickUseCases.createChick(requireContext().applicationContext, newChick)
                Log.d(this.javaClass.simpleName, "Method: $method ->\n$newChick")

                if (result) {
                    GenericToast.generateToast(
                        requireContext(),
                        getString(R.string.chick_publish_success),
                        Toast.LENGTH_LONG,
                        false
                    ).show()
                } else {
                    GenericToast.generateToast(
                        requireContext(),
                        getString(R.string.chick_publish_fail),
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            }

            binding.etTitle.text.clear()
            contentElements.clear()
            adapter.notifyDataSetChanged()

            //Permite cambiar el Fragmento Actual a otro.
            val navController = findNavController()
            navController.navigate(R.id.action_newChick_to_myChicks)

        }

    }

    //TODO Add Camera Option!
    /**
     * Launch Image Picker to select a Image to Upload.
     *
     */
    private fun launchImagePicker() {

// Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

        //OTHER TYPES

// Include only one of the following calls to launch(), depending on the types
// of media that you want to let the user choose from.

// Launch the photo picker and let the user choose images and videos.
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))

// Launch the photo picker and let the user choose only videos.
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))

// Launch the photo picker and let the user choose only images/videos of a
// specific MIME type, such as GIFs.
//        val mimeType = "image/gif"
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.SingleMimeType(mimeType)))

    }

}