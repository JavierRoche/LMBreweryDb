package com.tushe.lmbrewerydb.repository.db

import androidx.room.*
import com.tushe.lmbrewerydb.repository.model.Beer


/**
 * CONTIENE LOS ACCESOS A LA BD
 */

@Dao
abstract class DataAccessObject {
    @Query("SELECT * FROM beers_tb")
    abstract fun selectBeers(): List<Beer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBeer(beer: Beer)
/*
    @Delete
    abstract fun deleteCocktail(cocktail: DrinksItem)
*/}