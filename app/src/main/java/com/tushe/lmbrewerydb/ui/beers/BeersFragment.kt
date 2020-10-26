package com.tushe.lmbrewerydb.ui.beers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.common.CustomViewModel
import com.tushe.lmbrewerydb.repository.model.Beer
import com.tushe.lmbrewerydb.repository.network.BeerResponse
import com.tushe.lmbrewerydb.repository.network.BreweryDBApiService
import com.tushe.lmbrewerydb.repository.network.Category
import com.tushe.lmbrewerydb.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.beers_fragment.*
import kotlinx.android.synthetic.main.categories_activity.*
import retrofit2.Response

class BeersFragment : Fragment() , BeersAdapter.TapDelegate {
    // Para usar el adaptador y sus layouts
    private lateinit var adapter: BeersAdapter
    // Categoria actual
    private var currentCategoryID: Int? = null
    // Instancia del viewModel
    private val viewmodel: BeersViewModel by lazy {
        val factory = CustomViewModel(activity!!.application)
        ViewModelProvider(this, factory).get(BeersViewModel::class.java)
    }


    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
        const val onFailureMessage = "Something bad happened. Refresh list."
        // Instancia estatica del fragmento
        fun newInstance(category: Category): BeersFragment {
            val fragment = BeersFragment()
            val arguments = Bundle()
            // Recibimos el argumento desde la actividad a traves de un Bundle
            arguments.putSerializable(OBJECT_SERIALIZABLE, category)
            fragment.arguments = arguments
            return fragment
        }
    }


    /**
     * LIFE CYCLE
     */

    // Ejecuta una vez se ha creado la instancia del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.beers_fragment, container, false)
    }

    // Tanto la vista como el fragmento estan disponibles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Intentamos recuperar la categoria de los argumentos
        val category = this.arguments?.getSerializable(OBJECT_SERIALIZABLE) as Category

        remoteAccess(category.id)
        // Fijamos el titulo del topic en la parte superior del fragmento
        activity?.title = category.shortName
    }


    /**
     * PUBLIC FUNCTIONS
     */

    fun changesOnBeersList() {
        val emptyList: List<BeerResponse> = emptyList()
        // La actividad nos notifica de la vuelta de la vista de detalle
        val beersList: List<Beer> = viewmodel.filterByCategory(currentCategoryID, viewmodel.checkLocalDB(emptyList))
        // Pasamos al adaptador la lista actualizada
        (beersGridView.adapter as BeersAdapter).setChanges(beersList)
    }


    /**
     * PRIVATE FUNCTIONS
     */

    private fun remoteAccess(categoryId: Int?) {
        currentCategoryID = categoryId
        // Pedimos al viewmodel la lista de cervezas
        viewmodel.getBeersList(object: BreweryDBApiService.CallbackResponse<List<BeerResponse>> {
            override fun onResponse(response: List<BeerResponse>) {
                // Comprobamos la BD local antes de informar la UI
                initUI(viewmodel.filterByCategory(categoryId, viewmodel.checkLocalDB(response)))
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                Snackbar.make(container, onFailureMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun initUI(beersList: List<Beer>) {
        activity?.applicationContext?.let { context ->
            // Construimos el adaptador con el contexto, el listener de eventos y la lista de categorias
            adapter = BeersAdapter(context, this, beersList)
            // Asignamos al recicler el adaptador creado y el layout
            beersGridView.adapter = adapter
        }
    }


    /**
     * USER INTERACTIONS PROTOCOL
     */

    override fun onItemTap(beer: Beer?) {
        // Llamamos a la actividad de detalle pasandole la categoria seleccionada como objeto serializable
        activity?.let {
            Intent(it, DetailActivity::class.java).apply {
                putExtra(OBJECT_SERIALIZABLE, beer)
                it.startActivityForResult(this, 100)
            }
        }
    }
}