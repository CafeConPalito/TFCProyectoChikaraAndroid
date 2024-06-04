package com.cafeconpalito.chikara.ui.findChicks

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.R
import com.cafeconpalito.chikara.databinding.ItemChickPreviewBinding
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import com.cafeconpalito.chikara.ui.chik.ChikActivity
import com.cafeconpalito.chikara.utils.UserSession
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindChicksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChickPreviewBinding.bind(view)

    lateinit var chickDto: ChickDto;

    @Inject
    lateinit var chickUseCase: ChickUseCases

    private val context: Context = view.context

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
        binding.ivDeleteItem.setOnClickListener { deleteChick(onItemDone) }
    }

    private fun deleteChick(onItemDone: (Int) -> Unit) {

        //Lanzar la pregunta al Usuario.
        //if -> ok
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.AlertDialogTittle)
            .setMessage(R.string.AlertDialogMessage)
            .setPositiveButton(R.string.AlertDialogAccept) { dialog, which ->

                dialog.dismiss()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        withContext(Dispatchers.Main) {
                            //BORRA EL CHICK DE LA DB
                            chickUseCase.deleteChick(chickDto._id)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                //Borra el chick de la lista.
                onItemDone(adapterPosition)

//                ENVIA AL HOME EN ESTE CASO NO ES NECESARIO ENVIAR AL HOME
//                val intent = Intent(context, HomeActivity::class.java)
//                context.startActivity(intent)

            }
            .setNegativeButton(R.string.AlertDialogCancel) { dialog, which ->
                dialog.dismiss() // Cierra el diálogo si el usuario cancela
            }
            .show()

    }

    private fun goToChickActivity() {
        val context = itemView.context
        val intent = Intent(context, ChikActivity::class.java).apply {
            putExtra("chickDto", chickDto)
        }
        context.startActivity(intent)
    }

}