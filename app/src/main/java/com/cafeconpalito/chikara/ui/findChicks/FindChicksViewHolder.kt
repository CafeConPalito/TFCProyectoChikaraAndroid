package com.cafeconpalito.chikara.ui.findChicks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemChickBinding
import com.cafeconpalito.chikara.domain.model.ChickDto

class FindChicksViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemChickBinding.bind(view)

    //se ocupa de setear el texto de la tareas!
    //On ItemDone Borra La tarea, esta recibe como un Int la posicion de la lista (ID)
    fun render(chick: ChickDto, onItemDone:(Int) -> Unit){
        //Al arrancar Setea la imagen
        //TODO REVISAR COMO SETEAR SEGUN EL TIPO LA IMAGEN.
        //COMO CREO QUE SOLO LLEGAMOS A IMAGENES QUE CARGE LA URL DE LA PRIMERA.
        //CARGAR DE URL que viene en el VALUE
        //binding.ivChickImage.setImageBitmap(chick.content.get(0).value)
    }



}