package com.tushe.lmbrewerydb.repository.network

import androidx.room.Entity
import androidx.room.PrimaryKey
// Sino reconoce esta libreria es que faltan las dependencias de Retrofit
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoriesResponseModel(

        @field:SerializedName("data")
        var data: List<Category?>? = null

) : Serializable

// Para transformar la response del api en una tabla de Room
@Entity(tableName = "categories_tb")

data class Category(

        @PrimaryKey
        @field:SerializedName("id")
        val id: Int,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("shortName")
        val shortName: String? = null,

        @field:SerializedName("description")
        val description: String? = null

) : Serializable
