package com.tushe.lmbrewerydb.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.network.Category
import kotlinx.android.synthetic.main.category_cell.view.*

class CategoriesAdapter(private val selectedCellDelegate: TapDelegate,
                        private var categoriesList: List<Category>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesCellHolder>() {
    /**
     * PROTOCOL
     */

    interface TapDelegate {
        fun onItemTap(categoryId: Int)
    }


    /**
     * LIFE CYCLE
     */

    // Devolvera el ViewHolder plantilla para cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesCellHolder {
        // Le pasamos el recurso xml al que tiene que acceder como la vista que contendra cada celda
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_cell, parent, false)
        // Devolvemos el Holder indicando cual es la vista que manejara
        return CategoriesCellHolder(view)
    }

    // Conectamos el modelo de datos con nuestro ViewHolder
    override fun onBindViewHolder(holder: CategoriesCellHolder, position: Int) {
        // Recupero cada categoria por posicion y se lo asigno al holder
        val category = categoriesList[position]
        holder.category = category

        // Listener que escucha el tap en cada celda
        holder.view.setOnClickListener{
            selectedCellDelegate.onItemTap(category.id)
        }
    }

    // Numero de elementos de la lista
    override fun getItemCount(): Int {
        return categoriesList.size
    }


    /**
     * VIEW HOLDER
     */

    // La inner class hereda de ViewHolder y asi rellena el contenedor de la celda
    inner class CategoriesCellHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val view = view
        var category: Category? = null
            set(value) {
                field = value

                // Al tag del elemento de la vista le indicamos cada elemento de celda
                view.tag = field
                // Fijamos la informacion de la categoria
                field?.let { view.categoryNameTextView.text = it.shortName }
            }
    }
}