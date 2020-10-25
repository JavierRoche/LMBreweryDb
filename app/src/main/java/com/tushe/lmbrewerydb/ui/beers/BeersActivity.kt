package com.tushe.lmbrewerydb.ui.beers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.ui.categories.CategoriesFragment

class BeersActivity : AppCompatActivity() {
    /**
     * STATIC INIT
     */

    companion object {
        const val EXTRA_CATEGORY_ID = "CATEGORY_ID"
    }


    /**
     * LIFE CYCLE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos cual es el fichero xml que manejara la actividad
        setContentView(R.layout.beers_activity)

        // Intentamos recuperar la categoria seleccionada
        val categoryId: Int? = intent.getIntExtra(EXTRA_CATEGORY_ID, 1)

        // Comprobamos que sea la primera vez que se instancia la clase
        if (savedInstanceState == null && categoryId != null) {
            supportFragmentManager.beginTransaction().add(R.id.container, BeersFragment.newInstance(categoryId)).commitNow()
        }
    }
}