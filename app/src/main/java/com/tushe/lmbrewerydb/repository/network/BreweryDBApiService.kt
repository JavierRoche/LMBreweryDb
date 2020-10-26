package com.tushe.lmbrewerydb.repository.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BreweryDBApiService {
    // Elementos de comunicacion
    val categoriesApiService: AllCategoriesApi
    val beersApiService: AllBeersApi

    /**
     * DELEGATE PROTOCOL
     */

    interface CallbackResponse<T> {
        fun onResponse(response: T)
        fun onFailure(t: Throwable, res: Response<*>? = null)
    }


    /**
     * STATIC INIT
     */

    init {
        // Tiempo maximo de expiracion de la llamada que almacena el HttpClient
        val timeout: Long = 6 * 1000
        val client = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .build()

        // Elemento retrofit con el dominio y el seteo de Gson
        val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://sandbox-api.brewerydb.com")
                .build()

        // Creacion de la comunicacion con la tabla de categorias
        categoriesApiService = retrofit.create(AllCategoriesApi::class.java)
        // Creacion de la comunicacion con la tabla de cervezas
        beersApiService = retrofit.create(AllBeersApi::class.java)
    }
}