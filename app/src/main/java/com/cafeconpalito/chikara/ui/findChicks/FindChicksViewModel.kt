package com.cafeconpalito.chikara.ui.findChicks

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
class FindChicksViewModel @Inject constructor(private val chickUseCases: ChickUseCases) :
    ViewModel() {

    //La lista que vamos a devolver con la info
    val topChicksLiveData = MutableLiveData<MutableList<ChickDto>>()

    //La lista que mientras carga los datos esta mostrando datos Dummies
    val isLoading = MutableLiveData<Boolean>()

    //@Inject
    //lateinit var chickUseCases: ChickUseCases

    fun getTopChicks() {
        val method = object {}.javaClass.enclosingMethod?.name
        //Log.d("FindChicks", "Enter ViewModel -> getTopChick()")
        Log.d(this.javaClass.simpleName, "Method: $method -> start")

        viewModelScope.launch {

            isLoading.postValue(true)
            val response = chickUseCases.getTopChicks()
            topChicksLiveData.postValue(response.toMutableList())
            isLoading.postValue(false)

        }

    }

}