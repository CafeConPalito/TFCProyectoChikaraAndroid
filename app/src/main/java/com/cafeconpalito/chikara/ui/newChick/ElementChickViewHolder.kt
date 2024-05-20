package com.cafeconpalito.chikara.ui.newChick

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemElementChickBinding
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*

class ElementChickViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemElementChickBinding.bind(view)

    //se ocupa de setear el texto de la tareas!
    //On ItemDone Borra La tarea, esta recibe como un Int la posicion de la lista (ID)
    fun render(taskName:Uri, onItemDone:(Int) -> Unit){
        //Al arrancar Setea la imagen
        //binding.ivImagePreview.setImageBitmap()
        binding.ivImagePreview.setImageURI(taskName)
        binding.ivDeleteItem.setOnClickListener { onItemDone(adapterPosition) }
    }


}