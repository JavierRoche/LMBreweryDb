package com.example.cocktailclub.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.common.CustomViewModel
import com.tushe.lmbrewerydb.repository.model.Beer
import kotlinx.android.synthetic.main.detail_activity.*


class DetailActivity: AppCompatActivity() {
    // Instancia del viewModel
    private val viewmodel: DetailViewModel by lazy {
        val factory = CustomViewModel(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }


    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos el fichero xml que maneja la actividad
        setContentView(R.layout.detail_activity)


        // Intentamos recuperar la cerveza seleccionada
        intent.let {
            val beer: Beer? = it.extras?.getParcelable(OBJECT_SERIALIZABLE) as Beer?
            beer?.let { it ->
                // Pintado de la vista mediante el viewmodel
                viewmodel.beer = it
                viewmodel.fillFields(beerImage, productTextView, descriptionTextView, graduationTextView,
                                     favoriteButton, this@DetailActivity)
            }
        }

        // User Interaction
        favoriteButton.setOnClickListener {
            viewmodel.updateCocktail(favoriteButton)
            finish()
        }
    }
}


