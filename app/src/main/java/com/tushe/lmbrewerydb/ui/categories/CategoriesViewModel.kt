package com.tushe.lmbrewerydb.ui.categories

import android.app.Application
import androidx.lifecycle.ViewModel
import com.tushe.lmbrewerydb.repository.network.BreweryDBApiService
import com.tushe.lmbrewerydb.repository.network.CategoriesResponseModel
import com.tushe.lmbrewerydb.repository.network.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel(private val context: Application) : ViewModel() {
    // Listado de categorias
    private lateinit var categoriesList: List<Category>


    /**
     * STATIC INIT
     */

    companion object {
        const val API_KEY_VALUE: String = "3677be9ea8315cfb5c7c67c2430037a4"
    }


    /**
     * PUBLIC FUNCTIONS
     */

    fun getCategoriesList(callback: BreweryDBApiService.CallbackResponse<List<Category>>) {
        // Encolamos el servicio Api para recuperar la lista de categorias
        BreweryDBApiService().categoriesApiService.getCategoriesList(API_KEY_VALUE).enqueue(object : Callback<CategoriesResponseModel> {
            // Cuando el servicio Api AllCategoriesApi devuelve una respuesta correcta
            override fun onResponse(call: Call<CategoriesResponseModel>, response: Response<CategoriesResponseModel>) {
                // Comprobamos el 200 y que haya contenido en la respuesta para devolverla a la vista
                if (response.isSuccessful && response.body() != null) {
                    val apiResponse = response.body()!!
                    categoriesList = apiResponse.data as List<Category>
                    categoriesList.let { callback.onResponse(it) }

                } else {
                    callback.onFailure(Throwable(response.message()), response)
                }
            }

            // Cuando el servicio Api AllCategoriesApi devuelve una respuesta error
            override fun onFailure(call: Call<CategoriesResponseModel>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }
}