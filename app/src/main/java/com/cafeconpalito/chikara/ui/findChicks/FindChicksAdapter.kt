package com.cafeconpalito.chikara.ui.findChicks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.domain.model.ChickDto


/**
 * Adapter se ocupa de controlar las ViewHolder (TaskViewHolder)
 * Recibe el item done pasado desde el Main
 */
class FindChicksAdapter(
    private val elements: List<ChickDto>,
    private val onItemDone: (Int) -> Unit
) : RecyclerView.Adapter<FindChicksViewHolder>() {

    /**
     * Se ocupa de decir donde esta la vista (XML) para poder generarla y la devuelve.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindChicksViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return FindChicksViewHolder(layoutInflater.inflate(R.layout.item_chick_preview, parent, false))
    }

    /**
     * Se ocupa de generar la vista de del TaskViewHolder a medida que las necesita,
     * Para ello llama al metodo render.
     * El cual recibe un String con el texto de la Tarea, lo saca de la lista de TaskS
     */
    override fun onBindViewHolder(holder: FindChicksViewHolder, position: Int) {
        holder.render(elements[position], onItemDone)
    }

    /**
     * Devuelve el numero de objetos de la lista.
     */
    override fun getItemCount(): Int {
        return elements.size
    }

}