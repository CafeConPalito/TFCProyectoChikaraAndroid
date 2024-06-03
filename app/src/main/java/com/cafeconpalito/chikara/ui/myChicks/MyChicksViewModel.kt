package com.cafeconpalito.chikara.ui.myChicks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.useCase.ChickUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyChicksViewModel @Inject constructor(private val chickUseCases: ChickUseCases) :
    ViewModel() {

    //La lista que vamos a devolver con la info
    val userChicksLiveData = MutableLiveData<List<ChickDto>>()

    //La lista que mientras carga los datos esta mostrando datos Dummies
    val isLoading = MutableLiveData<Boolean>()

    //@Inject
    //lateinit var chickUseCases: ChickUseCases

    //TODO PARA LA BUSQUEDA SE PUEDE AÑADIR COMO PARAMETRO UN STRING A BUSCAR
    fun getUserChicks() {
        val method = object {}.javaClass.enclosingMethod?.name
        Log.d(this.javaClass.simpleName, "Method: $method -> start")
        //Log.d("UserChicks", "Enter ViewModel -> getTopChick()")

        viewModelScope.launch {

            isLoading.postValue(true)
            val response = chickUseCases.getUserChicks()
            userChicksLiveData.postValue(response)
            isLoading.postValue(false)

        }

    }

}