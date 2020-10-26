package com.tushe.lmbrewerydb.ui.beers

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tushe.lmbrewerydb.R
import com.tushe.lmbrewerydb.repository.model.Beer
import kotlinx.android.synthetic.main.beer_cell.view.*

class BeersAdapter(private val context: Context,
                   private val tapCellDelegate: TapDelegate,
                   private var beersList: List<Beer>) : BaseAdapter() {
    /**
     * PROTOCOL
     */

    interface TapDelegate {
        fun onItemTap(beer: Beer?)
    }


    /**
     * LIFE CYCLE
     */

    override fun getCount(): Int {
        return beersList.size
    }

    override fun getItem(position: Int): Any {
        return beersList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val beer = this.beersList[position]
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cell = inflator.inflate(R.layout.beer_cell, null)

        // Fijamos la informacion de la cerveza
        cell.productTextView.text = beer.nameTB
        if (beer.favoriteTB) {
            cell.favoriteImage.setImageResource(R.drawable.ic_favorite_accent_24)
        } else cell.favoriteImage.setImageResource(R.drawable.ic_no_favorite_accent_24)

        // Fijamos la imagen recuperada en formato url con Glide
        Glide.with(context)
            .load(beer.iconTB)
            .apply(RequestOptions().placeholder((R.mipmap.ic_default_beer)))
            .into(cell.photoImageView)

        cell.setOnClickListener {
            tapCellDelegate.onItemTap(beer)
        }

        return cell
    }


    /**
     * PUBLIC FUNCTIONS
     */

    // Metodo que repinta el modelo
    fun setChanges(beersList: List<Beer>) {
        this.beersList = beersList
        notifyDataSetChanged()
    }
}