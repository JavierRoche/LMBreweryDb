package com.tushe.lmbrewerydb.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.common.CustomViewModel
import com.tushe.lmbrewerydb.repository.network.BreweryDBApiService
import com.tushe.lmbrewerydb.repository.network.Category
import com.tushe.lmbrewerydb.ui.beers.BeersActivity
import kotlinx.android.synthetic.main.categories_activity.*
import kotlinx.android.synthetic.main.categories_fragment.*
import retrofit2.Response

class CategoriesFragment : Fragment(), CategoriesAdapter.TapDelegate {
    // Para usar el adaptador y sus layouts
    private lateinit var adapter: CategoriesAdapter
    // Instancia del viewModel
    private val viewmodel: CategoriesViewModel by lazy {
        val factory = CustomViewModel(activity!!.application)
        ViewModelProvider(this, factory).get(CategoriesViewModel::class.java)
    }


    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
        const val onFailureMessage = "Something bad happened. Refresh list."
        // Instancia estatica del fragmento
        fun newInstance(): CategoriesFragment {
            val fragment = CategoriesFragment()
            return fragment
        }
    }


    /**
     * LIFE CYCLE
     */

    // Ejecuta una vez se ha creado la instancia del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.categories_fragment, container, false)
    }

    // Tanto la vista como el fragmento estan disponibles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remoteAccess()
    }


    /**
     * PRIVATE FUNCTIONS
     */

    private fun remoteAccess() {
        // Pedimos al viewmodel la lista de categorias
        viewmodel.getCategoriesList(object: BreweryDBApiService.CallbackResponse<List<Category>> {
            override fun onResponse(response: List<Category>) {
                // Comprobamos la BD local antes de informar la UI
                initUI(response)
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                Snackbar.make(container, onFailureMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun initUI(categoriesList: List<Category>) {
        activity?.applicationContext?.let { context ->
            // Construimos el adaptador con el contexto, el listener de eventos y la lista de categorias
            adapter = CategoriesAdapter(this, categoriesList)
            // Asignamos al recicler el adaptador creado y el layout
            categoriesRecycler.adapter = adapter
            categoriesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    /**
     * USER INTERACTIONS PROTOCOL
     */

    override fun onItemTap(category: Category) {
        // Llamamos a la actividad de detalle pasandole la categoria seleccionada como objeto serializable
        activity?.let {
            Intent(it, BeersActivity::class.java).apply {
                putExtra(OBJECT_SERIALIZABLE, category)
                it.startActivity(this)
            }
        }
    }
}