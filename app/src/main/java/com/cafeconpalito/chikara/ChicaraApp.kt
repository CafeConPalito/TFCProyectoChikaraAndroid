package com.cafeconpalito.chikara

import android.app.Application
import android.util.Log
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ChicaraApp: Application() {

}