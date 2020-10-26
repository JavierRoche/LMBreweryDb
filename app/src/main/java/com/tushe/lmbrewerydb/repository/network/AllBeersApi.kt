package com.tushe.lmbrewerydb.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * ALL BEERS API
 */

interface AllBeersApi {
    companion object {
        const val KEY_VALUE: String = "key"
    }

    @GET("v2/beers")
    @Headers("Content-Type: application/json")

    fun getBeersList(@Query(KEY_VALUE) apiKeyValue: String): Call<BeersResponseModel>
}