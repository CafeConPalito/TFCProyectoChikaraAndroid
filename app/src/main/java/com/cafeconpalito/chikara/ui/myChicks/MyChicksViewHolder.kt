package com.cafeconpalito.chikara.ui.myChicks

import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.databinding.ItemChickPreviewBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.ui.chik.ChikActivity
import com.cafeconpalito.chikara.utils.UserSession
import com.squareup.picasso.Picasso

class MyChicksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChickPreviewBinding.bind(view)

    lateinit var chickDto: ChickDto;

    //se ocupa de setear los elementos de la vista
    //On ItemDone Borra La elementos, esta recibe como un Int la posicion de la lista (ID)
    fun render(chick: ChickDto, onItemDone: (Int) -> Unit) {
        chickDto = chick

        //SET TITTLE
        binding.tvTitle.text = chick.title
        //SET IMAGE
        Picasso.get().load(chick.content[0].value).into(binding.ivChickImage)
        //SET AUTHOR
        binding.tvAuthor.text = chick.author_name

        //Si el autor es usuario activa el boton de borrar.
        if (UserSession.userUUID.equals(chick.author)) {
            binding.ivDeleteItem.isVisible = true
        }

        initListeners(onItemDone)
    }

    private fun initListeners(onItemDone: (Int) -> Unit) {
        binding.ivChickImage.setOnClickListener { goToChickActivity() }
        //BORRAR EL CHICK
        binding.ivDeleteItem.setOnClickListener { onItemDone(adapterPosition) }
    }

    private fun goToChickActivity() {
        val context = itemView.context
        val intent = Intent(context, ChikActivity::class.java).apply {
            putExtra("chickDto", chickDto)
        }
        context.startActivity(intent)
    }

}