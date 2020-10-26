package com.tushe.lmbrewerydb.ui.detail

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.db.BeersRoom
import com.tushe.lmbrewerydb.repository.model.Beer

class DetailViewModel(private val context: Application) : ViewModel() {
    // Instancia de la cerveza seleccionada
    lateinit var beer: Beer

    /**
     * PUBLIC FUNCTIONS
     */

    // Rellena la informacion de pantalla
    @SuppressLint("SetTextI18n")
    fun fillFields(beerImage: ImageView, productTextView: TextView, descriptionTextView: TextView, graduationTextView: TextView,
                   favoriteButton: FloatingActionButton, context: Context) {
        beer.let {
            // Fijamos la informacion de la beer
            productTextView.text = beer.nameTB
            graduationTextView.text = "${graduationTextView.text}${beer.abvTB}"
            descriptionTextView.text = beer.descriptionTB

            // Fijamos la imagen recuperada en formato url con Glide
            Glide.with(context)
                .load(it.mediumTB)
                .apply(RequestOptions().placeholder((R.mipmap.ic_default_beer)))
                .into(beerImage)

            if (isFavoriteBeer()) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_white_24)
            } else favoriteButton.setImageResource(R.drawable.ic_no_favorite_white_24)
        }
    }

    // Accede a Room para guardar la beer como favorita
    fun updateBeer(favoriteButton: FloatingActionButton) {
        beer.let {
            // Cambiamos el valor del objeto y lo guardamos en Room
            it.favoriteTB = !it.favoriteTB
            val updatedBeer = it
            // Sobreescribimos la cerveza en la tabla
            BeersRoom.getDBInstance(context).dataAccessObject().insertBeer(updatedBeer)
            // Fijamos el icono de favorito
            if (isFavoriteBeer()) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_white_24)
            } else favoriteButton.setImageResource(R.drawable.ic_no_favorite_white_24)
        }
    }


    /**
     * PRIVATE FUNCTIONS
     */

    private fun isFavoriteBeer() : Boolean {
        beer.let { return it.favoriteTB }
    }
}