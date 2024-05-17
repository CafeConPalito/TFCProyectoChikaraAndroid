package com.cafeconpalito.chikara.ui.newChick

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentNewChickBinding
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import com.cafeconpalito.chikara.utils.EncodeBase64
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.LinkedList


@AndroidEntryPoint
class NewChickFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentNewChickBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectedImageUri: Uri

    //Launcher encargado de obtener la Uri de la imagen seleccinada
    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->

        //Si la imagen fue seleccionada
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")

            //PARA SETEAR DIRECTAMENTE LA IMAGEN
            //binding.imageView.setImageURI(uri)

            selectedImageUri = uri

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
    }

    private fun initListeners() {
        binding.btnPublish.setOnClickListener {
            publishNewChick()
        }

        binding.btnAddElement.setOnClickListener {
            launchImagePicker()
        }
    }

    private fun publishNewChick() {

        val encodeBase64 = EncodeBase64()

        var base64String : String

        CoroutineScope(Dispatchers.Main).launch {
            try {
                base64String =
                    encodeBase64(requireContext(), selectedImageUri)
                Log.d("PhotoPicker", "Base64 String: $base64String")

            } catch (e: Exception) {
                Log.e("PhotoPicker", "Error processing image", e)
            }
        }

    }

    /**
     * Launch Image Picker to select a Image to Upload.
     *
     */
    private fun launchImagePicker() {

// Include only one of the following calls to launch(), depending on the types
// of media that you want to let the user choose from.

// Launch the photo picker and let the user choose images and videos.
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageAndVideo))

// Launch the photo picker and let the user choose only images.
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

// Launch the photo picker and let the user choose only videos.
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.VideoOnly))

// Launch the photo picker and let the user choose only images/videos of a
// specific MIME type, such as GIFs.
//        val mimeType = "image/gif"
//        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.SingleMimeType(mimeType)))


    }

    /**
     * Generate Data for testing
     */
    private fun generateChickDto(): ChickDto {

        val content = generateChickContentDto()

        return ChickDto(
            title = "Test Chick Image",
            isprivate = false,
            content = content
        )

    }

    /**
     * Generate Data for testing
     */
    private fun generateChickContentDto(): List<ChickContentDto> {

        val list: MutableList<ChickContentDto> = LinkedList()

        val content = listOf(

            ChickContentDto(
                position = 1,
                value = "",
                type = ChickTypeDto.TYPE_IMG
            )
        )

        list.addAll(content)
        return list

    }

}