package com.cafeconpalito.chikara.ui.myChicks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemChickPreviewBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.squareup.picasso.Picasso

class MyChicksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChickPreviewBinding.bind(view)

    //se ocupa de setear los elementos de la vista
    //On ItemDone Borra La tarea, esta recibe como un Int la posicion de la lista (ID)
    fun render(chick: ChickDto, onItemDone: (Int) -> Unit) {

        //SET TITTLE
        binding.tvTitle.text = chick.title
        //SET IMAGE
        Picasso.get().load(chick.content[0].value).into(binding.ivChickImage)
        //SET AUTHOR
        binding.tvAuthor.text = chick.author_name

    }


}