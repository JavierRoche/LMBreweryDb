package com.tushe.lmbrewerydb.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * ALL CATEGORIES API
 */

interface AllCategoriesApi {
    companion object {
        const val KEY_VALUE: String = "key"
    }

    @GET("v2/styles")
    @Headers("Content-Type: application/json")

    fun getCategoriesList(@Query(KEY_VALUE) apiKeyValue: String): Call<CategoriesResponseModel>
}