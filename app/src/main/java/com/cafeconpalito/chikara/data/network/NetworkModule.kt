package com.cafeconpalito.chikara.data.network

import com.cafeconpalito.chikara.BuildConfig.BASE_URL
import com.cafeconpalito.chikara.data.network.repositoryImpl.ChickRepositoryImpl
import com.cafeconpalito.chikara.data.network.repositoryImpl.LoginRepositoryImpl
import com.cafeconpalito.chikara.data.network.repositoryImpl.UserRepositoryImpl
import com.cafeconpalito.chikara.data.network.service.ChickApiService
import com.cafeconpalito.chikara.data.network.service.LoginApiService
import com.cafeconpalito.chikara.data.network.service.UserApiService
import com.cafeconpalito.chikara.domain.repository.ChickRepository
import com.cafeconpalito.chikara.domain.repository.LoginRepository
import com.cafeconpalito.chikara.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Tiene la conexion a la API utilizando retrofit
 * Tipo de objeto SINGELTON :D
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Auth Key Para poder acceder a los endpoints de la App
    var AuthKey: String = ""

    /**
     * SINGELTON
     * Crea la conexion necesaria a la API
     * Recibe como parametro un interceptor para obtener la informacion de la consulta.
     *
     * BASE_URL VIENE DEL GRADEL, POR EL TIPO DE CONFIGURACION!
     */
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {

        //Sin Autorizacion
        /*
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        */

        //CON Autorizacion

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("authorization", "Bearer ${AuthKey}")
                    .addHeader("phone-id", android.os.Build.ID)
                    .addHeader("phone-model", android.os.Build.MODEL)
                    .addHeader("phone-brand", android.os.Build.BRAND)
                    .build()
                chain.proceed(request)
            }.build())
            .build()

    }

    /**
     * Interceptor se utilizan para obtener toda la respuesta de la Red,
     * La guarda en el LOG permite ver que pasa con las llamadas
     * Segun el nivel podemos obtener mas o menos info
     */
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        //Este nivel de interceptor captura toda la respuesda de la Api
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    /**
     * Construye el repositorio de API SERVICE
     */
    @Provides
    fun providerLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    /**
     * Para difenrecioar entre repositorios utilizamos la Notacion @Name
     */
    //@Named
    @Provides
    fun provideLoginRepository(apiService: LoginApiService): LoginRepository {
        return LoginRepositoryImpl(apiService)
    }

    /**
     * Construye el repositorio de API SERVICE
     */
    @Provides
    fun providerUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    /**
     * Provee lo necesario para contruir la clase
     */
    @Provides
    fun provideUserRepository(apiService: UserApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    /**
     * Construye el repositorio de API SERVICE
     */
    @Provides
    fun providerChickApiService(retrofit: Retrofit): ChickApiService {
        return retrofit.create(ChickApiService::class.java)
    }

    /**
     * Provee lo necesario para contruir la clase
     */
    @Provides
    fun provideChickRepository(apiService: ChickApiService): ChickRepository {
        return ChickRepositoryImpl(apiService)
    }


}