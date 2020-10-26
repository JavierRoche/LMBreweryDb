package com.tushe.lmbrewerydb.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.model.Beer

class DetailActivity: AppCompatActivity(), DetailFragment.ZoomInteractionListener,
                                           ZoomDetailFragment.ZoomInteractionListener {
    // Definimos los objetos de cada fragmento que usara la actividad
    private lateinit var detailFragment: DetailFragment
    private lateinit var zoomDetailFragment: ZoomDetailFragment
    // La cerveza de detalle
    private var beer: Beer? = null

    /**
     * LIFE CYCLE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos cual es el fichero xml que manejara la actividad
        setContentView(R.layout.detail_activity)

        // Intentamos recuperar la cerveza seleccionada
        beer = intent.extras?.getParcelable(DetailFragment.OBJECT_SERIALIZABLE) as Beer?
        beer?.let {
            detailFragment = DetailFragment.newInstance(it)
            zoomDetailFragment = ZoomDetailFragment.newInstance(it)
        }

        // Comprobamos que sea la primera vez que se instancia la clase
        if (savedInstanceState == null && beer != null)
            supportFragmentManager.beginTransaction().add(R.id.container, detailFragment).commitNow()
    }


    /**
     * USER ACTIONS
     */

    override fun onZoomInImage() {
        // Reemplazamos el fragmento con animacion
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, zoomDetailFragment)
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out)
                .commit()
    }

    override fun onZoomOutImage() {
        // Reemplazamos el fragmento
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, detailFragment)
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out)
                .commit()
    }
}