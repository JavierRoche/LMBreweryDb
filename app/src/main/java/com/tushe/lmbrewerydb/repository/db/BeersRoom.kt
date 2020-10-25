package com.tushe.lmbrewerydb.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tushe.lmbrewerydb.repository.model.Beer

/**
 * MODELO DE DATOS
 */

// Se añaden las tablas en el array
@Database(entities = [Beer::class], version = 1, exportSchema = false)

abstract class BeersRoom : RoomDatabase() {
    // Singleton para el acceso a la instancia de la BD
    companion object {
        // Instancia de la base de datos
        private var DBInstance: BeersRoom? = null

        fun getDBInstance(context: Context): BeersRoom {
            if (DBInstance == null) {
                synchronized(BeersRoom::class) {
                    // Creacion de la instancia de la DB de Room
                    DBInstance = Room.databaseBuilder(
                        context.applicationContext,
                        BeersRoom::class.java,
                        "BeersDB"
                    )
                        // Sirve para destruir y recrear la BD ante un cambio de version
                        .fallbackToDestructiveMigration()
                        // Para hacer queries sobre la BD obliga a que sea en background y no se pueda usar el hilo principal
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return DBInstance!!
        }
    }

    abstract fun dataAccessObject(): DataAccessObject
}