package com.cafeconpalito.chikara.ui.chik

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemChickBinding
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import com.squareup.picasso.Picasso

class ChickViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChickBinding.bind(view)

    //se ocupa de setear los elementos.
    //On ItemDone Borra La tarea, esta recibe como un Int la posicion de la lista (ID)
    fun render(chickContentDto: ChickContentDto, onItemDone: (Int) -> Unit) {

        //Si es de tipo imagen cargar la imagen
        if (chickContentDto.type.equals(ChickTypeDto.TYPE_IMG)) {
            Picasso.get().load(chickContentDto.value).into(binding.ivChickImage)
        }

    }

}