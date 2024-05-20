package com.cafeconpalito.chikara.ui.newChick

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.R


/**
 * Adapter se ocupa de controlar las ViewHolder (TaskViewHolder)
 * Recibe el item done pasado desde el Main
 */
//Para operar con tasks, la lista de tareas, private val!
class ElementChickAdapter (private val elements:List<Uri>, private val onItemDone:(Int)-> Unit): RecyclerView.Adapter<ElementChickViewHolder>() {

    /**
     * Devuelve el numero de objetos de la lista.
     */
    override fun getItemCount(): Int {
        return elements.size
    }

    /**
     * Se ocupa de generar la vista de del TaskViewHolder a medida que las necesita,
     * Para ello llama al metodo render.
     * El cual recibe un String con el texto de la Tarea, lo saca de la lista de TaskS
     */
    override fun onBindViewHolder(holder: ElementChickViewHolder, position: Int) {
        holder.render(elements[position], onItemDone)
    }


    /**
     * Se ocupa de decir donde esta la vista (XML) para poder generarla y la devuelve.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementChickViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ElementChickViewHolder(layoutInflater.inflate(R.layout.item_element_chick,parent,false))
    }

}