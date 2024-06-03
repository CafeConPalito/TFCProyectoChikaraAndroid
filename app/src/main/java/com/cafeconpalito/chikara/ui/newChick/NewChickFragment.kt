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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.cafeconpalito.chikara.databinding.FragmentNewChickBinding
import com.cafeconpalito.chikara.domain.assembler.ChickDtoAssembler
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.utils.DotIndicatorDecoration
import com.cafeconpalito.chikara.ui.utils.GenericToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject


@AndroidEntryPoint
class NewChickFragment : Fragment() {

    //TODO ADD DRAG AND DROP FOR THE ELEMENTS!

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
    private fun publishNewChick() {
        if (contentElements.isEmpty()) {
            GenericToast.generateToast(
                requireContext(),
                "AÑADE UN TITULO!!!!!!",
                Toast.LENGTH_LONG,
                true
            ).show()

        } else if (binding.etTitle.text.isBlank()) {
            GenericToast.generateToast(
                requireContext(),
                "AÑADE UN ELEMENTOS!!!!!!",
                Toast.LENGTH_LONG,
                true
            ).show()
        } else {

            val chickDtoAssembler = ChickDtoAssembler()

            val title = binding.etTitle.text.toString()
            val isPrivate = binding.sIsPrivate.isChecked
            val newChick = chickDtoAssembler.buildChickDto(title,isPrivate, contentElements)

            //TODO VIEJO PARA BORRAR
            //val newChick = generateChickDto(elements)

            CoroutineScope(Dispatchers.Main).launch {
                //Envio el contexto de la Aplicacion!
                chickUseCases.createChick(requireContext().applicationContext, newChick)
                Log.d("PhotoPicker", "Chick Publish $newChick")
            }

        }

    }

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


    //TODO MOVIDO A Assembler PROBAR Y BORRAR!
    /**
     * Generate Data for new Chick
     */
    private fun generateChickDto(elements: List<Uri>): ChickDto {

        val content = generateChickContentDto(elements)
        return ChickDto(
            title = binding.etTitle.text.toString(),
            isprivate = binding.sIsPrivate.isChecked,
            content = content
        )
    }

    //TODO MOVIDO A Assembler PROBAR Y BORRAR!
    /**
     * Generate Data for Content Chick
     */
    private fun generateChickContentDto(elements: List<Uri>): List<ChickContentDto> {
        //List to return of Content
        val list: MutableList<ChickContentDto> = LinkedList()
        //Position of element
        var pos: Long = 0
        //For each element add to list
        for (uri in elements) {
            //position
            pos++
            val content = ChickContentDto(
                position = pos,
                value = uri.toString(),
                type = ChickTypeDto.TYPE_IMG
            )
            list.add(content)
        }
        return list
    }

}