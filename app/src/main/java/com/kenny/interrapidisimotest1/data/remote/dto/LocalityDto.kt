package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LocalityDto(
    @SerializedName("AbreviacionCiudad") val cityAbbreviation: String?,
    @SerializedName("NombreCompleto") val fullName: String?,
    @SerializedName("Id") val id: Int?,
    @SerializedName("CodigoCiudad") val cityCode: String?,
)