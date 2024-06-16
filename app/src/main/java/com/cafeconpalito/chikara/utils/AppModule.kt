package com.cafeconpalito.chikara.utils

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Provider the Application Context using DaggerHilt Injection
     */
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

}

/*
//TODO: Con esta injection en una clase puedes insertar el contexto de la app
class DotIndicatorDecoration @Inject constructor(
@ApplicationContext private val context: Context
) : RecyclerView.ItemDecoration() {

// Usa el contexto aquí según sea necesario
}
*/

