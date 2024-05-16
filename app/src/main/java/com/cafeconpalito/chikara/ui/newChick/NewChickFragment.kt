package com.cafeconpalito.chikara.ui.newChick

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.cafeconpalito.chikara.databinding.FragmentNewChickBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.result.contract.ActivityResultContracts.*

@AndroidEntryPoint
class NewChickFragment : Fragment() {


    //Manera de trabajar con Binding y Fragmentos
    private var _binding: FragmentNewChickBinding? = null
    private val binding get() = _binding!!


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
        binding.btnAddElement.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

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

        println(pickMedia)

    }



}