package com.cafeconpalito.chikara.ui.newChick

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemElementNewChickBinding

class ElementChickViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemElementNewChickBinding.bind(view)

    //se ocupa de setear la informacion del view!
    //On ItemDone Borra el elemento, esta recibe como un Int la posicion de la lista (ID)
    fun render(taskName: Uri, onItemDone: (Int) -> Unit) {
        //Al arrancar Setea la imagen
        //binding.ivImagePreview.setImageBitmap()
        binding.ivImagePreview.setImageURI(taskName)
        binding.ivDeleteItem.setOnClickListener { onItemDone(adapterPosition) }
    }


}