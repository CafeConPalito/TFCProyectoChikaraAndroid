package com.cafeconpalito.chikara.ui.myChicks

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemChickPreviewBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.ui.chik.ChikActivity
import com.squareup.picasso.Picasso

class MyChicksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChickPreviewBinding.bind(view)

    lateinit var chickDto: ChickDto;


    //se ocupa de setear los elementos de la vista
    //On ItemDone Borra La tarea, esta recibe como un Int la posicion de la lista (ID)
    fun render(chick: ChickDto, onItemDone: (Int) -> Unit) {

        chickDto = chick

        //SET TITTLE
        binding.tvTitle.text = chick.title
        //SET IMAGE
        Picasso.get().load(chick.content[0].value).into(binding.ivChickImage)
        //SET AUTHOR
        binding.tvAuthor.text = chick.author_name

        initListeners()
    }

    private fun initListeners() {
        binding.ivChickImage.setOnClickListener { goToChickActivity() }
    }

    private fun goToChickActivity() {
        val context = itemView.context
        val intent = Intent(context, ChikActivity::class.java).apply {
            putExtra("chickDto", chickDto)
        }
        context.startActivity(intent)
    }


}