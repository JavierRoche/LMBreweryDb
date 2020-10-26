package com.tushe.lmbrewerydb.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.model.Beer
import kotlinx.android.synthetic.main.detail_zoom_fragment.*

class ZoomDetailFragment : Fragment() {
    // Definimos el InteractionListener que recogera los eventos de usuario sobre el fragmento
    private var zoomOutInteractionListener: ZoomInteractionListener? = null

    /**
     * PROTOCOLS
     */

    // Protocolo con los metodos abstractos del fragmento que recogen las interaciones del usuario y que seran sobreescritos en la actividad
    interface ZoomInteractionListener {
        fun onZoomOutImage()
    }

    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
        // Instancia estatica del fragmento
        fun newInstance(beer: Beer): ZoomDetailFragment {
            val fragment = ZoomDetailFragment()
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
            zoomOutInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${ZoomInteractionListener::class.java.canonicalName}")
    }

    // Ejecuta una vez se ha creado la instancia del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.detail_zoom_fragment, container, false)
    }

    // Tanto la vista como el fragmento estan disponibles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beer = this.arguments?.getParcelable(OBJECT_SERIALIZABLE) as Beer?

        beer?.let { it ->
            // Fijamos la imagen recuperada en formato url con Glide
            Glide.with(activity!!.applicationContext)
                .load(it.contentAwareLargeTB)
                .apply(RequestOptions().placeholder((R.mipmap.ic_default_beer)))
                .into(beerImage)
        }

        beerImage.setOnClickListener {
            println("clic image beer")
            zoomOutInteractionListener?.onZoomOutImage()
        }
    }
}