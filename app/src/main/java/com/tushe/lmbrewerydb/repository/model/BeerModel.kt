package com.tushe.lmbrewerydb.repository.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tushe.lmbrewerydb.repository.network.BeerResponse
import kotlinx.android.parcel.Parcelize


@Parcelize
// Para transformar la response del api en una tabla de Room
@Entity(tableName = "beers_tb")

data class Beer (
    @PrimaryKey(autoGenerate = false)
    var idTB: String = "",
    var nameTB: String = "",
    var descriptionTB: String? = "",
    var abvTB: String = "",
    var styleIdTB: Int? = 0,
    var isOrganicTB: String = "",
    var iconTB: String? = "",
    var mediumTB: String? = "",
    var largeTB: String? = "",
    var contentAwareIconTB: String? = "",
    var contentAwareMediumTB: String? = "",
    var contentAwareLargeTB: String? = "",
    var favoriteTB: Boolean = false) : Parcelable {

    // Los metodos aqui incluidos estan declarados en un contexto estatico
    companion object {
        fun parseBeersList(beersList: List<BeerResponse>): List<Beer> {
            // Definimos la lista mutable del modelo de datos
            val beers = mutableListOf<Beer>()
            // Iteramos los topics de la respuesta del servidor
            beersList.map { item ->
                beers.add(parseBeer(item))
            }

            return beers
        }

        private fun parseBeer(beerResponse: BeerResponse): Beer {
            //val auxDescription: String = beerResponse.description ?: ""
            //val auxStyleId: Int = beerResponse.styleId ?: 0

            // Devolvemos el modelo de datos del topic con los datos extraidos del BeerResponse
            return Beer(
                idTB = beerResponse.id ?: "",
                nameTB = beerResponse.name ?: "",
                descriptionTB = beerResponse.description ?: "",
                abvTB = beerResponse.abv ?: "",
                styleIdTB = beerResponse.styleId ?: 0,
                isOrganicTB = beerResponse.isOrganic ?: "N",
                iconTB = beerResponse.labels?.icon ?: "",
                mediumTB = beerResponse.labels?.medium ?: "",
                largeTB = beerResponse.labels?.large ?: "",
                contentAwareIconTB = beerResponse.labels?.contentAwareIcon ?: "",
                contentAwareMediumTB = beerResponse.labels?.contentAwareMedium ?: "",
                contentAwareLargeTB = beerResponse.labels?.contentAwareLarge ?: ""
            )
        }
    }
}
