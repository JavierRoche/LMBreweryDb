package com.tushe.lmbrewerydb.ui.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tushe.lmbrewerydb.R

class CategoriesActivity : AppCompatActivity() {
    /**
     * LIFE CYCLE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos cual es el fichero xml que manejara la actividad
        setContentView(R.layout.categories_activity)

        // Comprobamos que sea la primera vez que se instancia la clase
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.container, CategoriesFragment.newInstance()).commitNow()
    }
}