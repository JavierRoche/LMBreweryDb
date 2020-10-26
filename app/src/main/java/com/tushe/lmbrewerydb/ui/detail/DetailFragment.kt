package com.tushe.lmbrewerydb.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.common.CustomViewModel
import com.tushe.lmbrewerydb.repository.model.Beer
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {
    // Definimos el InteractionListener que recogera los eventos de usuario sobre el fragmento
    private var zoomInInteractionListener: ZoomInteractionListener? = null

    // Instancia del viewModel
    private val viewmodel: DetailViewModel by lazy {
        val factory = CustomViewModel(activity!!.application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }


    /**
     * PROTOCOLS
     */

    // Protocolo con los metodos abstractos del fragmento que recogen las interaciones del usuario y que seran sobreescritos en la actividad
    interface ZoomInteractionListener {
        fun onZoomInImage()
    }

    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
        // Instancia estatica del fragmento
        fun newInstance(beer: Beer): DetailFragment {
            val fragment = DetailFragment()
            val arguments = Bundle()
            // Recibimos el argumento desde la actividad a traves de un Bundle
            arguments.putParcelable(OBJECT_SERIALIZABLE, beer)
            fragment.arguments = arguments
            return fragment
        }
    }


    /**
     * LIFE CYCLE
     */

    // Liga nuestro fragmento a su actividad
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Comprobamos que el contexto de la actividad implementa el protocolo
        if (context is ZoomInteractionListener)
            zoomInInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${ZoomInteractionListener::class.java.canonicalName}")
    }

    // Ejecuta una vez se ha creado la instancia del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    // Tanto la vista como el fragmento estan disponibles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Intentamos recuperar la categoria de los argumentos
        val beer = this.arguments?.getParcelable(OBJECT_SERIALIZABLE) as Beer?

        beer?.let { it ->
            // Pintado de la vista mediante el viewmodel
            viewmodel.beer = it
            viewmodel.fillFields(beerImage, productTextView, descriptionTextView, graduationTextView,
                favoriteButton, activity!!.applicationContext
            )
        }

        // User Interaction sobre el botond de favoritos
        favoriteButton.setOnClickListener {
            viewmodel.updateBeer(favoriteButton)
        }

        beerImage.setOnClickListener {
            zoomInInteractionListener?.onZoomInImage()
        }
    }
}