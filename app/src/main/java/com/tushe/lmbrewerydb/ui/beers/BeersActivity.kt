package com.tushe.lmbrewerydb.ui.beers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.network.Category

class BeersActivity : AppCompatActivity() {
    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
    }


    /**
     * LIFE CYCLE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos cual es el fichero xml que manejara la actividad
        setContentView(R.layout.beers_activity)

        // Intentamos recuperar la categoria seleccionada
        val category = intent.extras?.getSerializable(OBJECT_SERIALIZABLE) as Category

        // Comprobamos que sea la primera vez que se instancia la clase
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, BeersFragment.newInstance(category)).commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Podemos acceder a una funcion del fragmento a traves del supportFragmentManager
        if (requestCode == 100)
            (supportFragmentManager.findFragmentById(R.id.container) as BeersFragment).changesOnBeersList()
    }
}