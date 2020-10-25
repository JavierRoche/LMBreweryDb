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
import kotlinx.android.synthetic.main.beer_cell.view.productTextView
import kotlinx.android.synthetic.main.detail_activity.view.*

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

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val beer = this.beersList[position]
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val cell = inflator.inflate(R.layout.beer_cell, null)

        // Fijamos la informacion de la cerveza
        cell.productTextView.text = beer.nameTB

        // Fijamos la imagen recuperada en formato url con Glide
        Glide.with(context)
            .load(beer.mediumTB)
            .apply(RequestOptions().placeholder((R.drawable.ic_launcher_background)))
            .into(cell.photoImageView)

        cell.setOnClickListener {
            tapCellDelegate.onItemTap(beer)
        }

        return cell
    }
}