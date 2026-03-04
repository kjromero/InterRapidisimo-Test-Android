package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ParametroDto(
    @SerializedName("Nombre") val name: String?,
    @SerializedName("Valor") val value: String?,
    @SerializedName("Descripcion") val description: String?,
)