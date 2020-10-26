package com.tushe.lmbrewerydb.ui.beers

import android.app.Application
import androidx.lifecycle.ViewModel
import com.tushe.lmbrewerydb.repository.db.BeersRoom
import com.tushe.lmbrewerydb.repository.model.Beer
import com.tushe.lmbrewerydb.repository.model.Beer.Companion.parseBeersList
import com.tushe.lmbrewerydb.repository.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeersViewModel(private val context: Application) : ViewModel() {
    // Listado de cervezas
    private lateinit var beersList: List<Beer>


    /**
     * STATIC INIT
     */

    companion object {
        const val API_KEY_VALUE: String = "3677be9ea8315cfb5c7c67c2430037a4"
    }


    /**
     * PUBLIC FUNCTIONS
     */

    fun getBeersList(callback: BreweryDBApiService.CallbackResponse<List<BeerResponse>>) {
        // Encolamos el servicio Api para recuperar la lista de categorias
        BreweryDBApiService().beersApiService.getBeersList(API_KEY_VALUE).enqueue(object : Callback<BeersResponseModel> {
            // Cuando el servicio Api AllCategoriesApi devuelve una respuesta correcta
            override fun onResponse(call: Call<BeersResponseModel>, response: Response<BeersResponseModel>) {
                // Comprobamos el 200 y que haya contenido en la respuesta para devolverla a la vista
                if (response.isSuccessful && response.body() != null) {
                    val apiResponse = response.body()!!
                    val beersList = apiResponse.data as List<BeerResponse>
                    beersList.let { callback.onResponse(it) }

                } else {
                    callback.onFailure(Throwable(response.message()), response)
                }
            }

            // Cuando el servicio Api AllCategoriesApi devuelve una respuesta error
            override fun onFailure(call: Call<BeersResponseModel>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }

    // Recibe la lista de beers en formato modelo y la devuelve en formato tabla
    fun checkLocalDB(remoteBeersList: List<BeerResponse>) : List<Beer> {
        // Conseguimos una lista de beers definitiva conforme a si existe la BD en Room
        val roomBeersList = BeersRoom.getDBInstance(context).dataAccessObject().selectBeers()
        beersList = if (roomBeersList.isEmpty()) {
            saveBeers(remoteBeersList)
        } else roomBeersList
        return beersList
    }

    fun filterByCategory(categoryId: Int?, beersList: List<Beer>): List<Beer> {
        val filteredBeersList: MutableList<Beer> = arrayListOf()
        beersList.map {
            if (it.styleIdTB == categoryId) filteredBeersList.add(0, it)
        }
        return filteredBeersList
    }


    /**
     * PRIVATE FUNCTIONS
     */

    // Accede a Room para guardar la beer
    private fun saveBeers(remoteBeersList: List<BeerResponse>) : List<Beer> {
        val formattedBeersList: List<Beer> = parseBeersList(remoteBeersList)
        formattedBeersList.map { item ->
            // Insertamos la beer en la tabla de Room
            BeersRoom.getDBInstance(context).dataAccessObject().insertBeer(item)
        }
        return formattedBeersList
    }
}