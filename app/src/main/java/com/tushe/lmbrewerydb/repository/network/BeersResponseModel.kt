package com.tushe.lmbrewerydb.repository.network

// Sino reconoce esta libreria es que faltan las dependencias de Retrofit
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BeersResponseModel(

	@field:SerializedName("data")
	var data: List<BeerResponse?>? = null

) : Serializable

data class BeerResponse(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("abv")
	val abv: String? = null,

	@field:SerializedName("styleId")
	val styleId: Int? = null,

	@field:SerializedName("isOrganic")
	val isOrganic: String? = null,

	@field:SerializedName("labels")
	val labels: Labels? = null,

) : Serializable

data class Labels(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("medium")
	val medium: String? = null,

	@field:SerializedName("large")
	val large: String? = null,

	@field:SerializedName("contentAwareIcon")
	val contentAwareIcon: String? = null,

	@field:SerializedName("contentAwareMedium")
	val contentAwareMedium: String? = null,

	@field:SerializedName("contentAwareLarge")
	val contentAwareLarge: String? = null

) : Serializable