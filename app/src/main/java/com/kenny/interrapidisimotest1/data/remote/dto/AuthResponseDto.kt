package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("Usuario") val username: String?,
    @SerializedName("Identificacion") val identification: String?,
    @SerializedName("Nombre") val name: String?,
    @SerializedName("Token") val token: String?,
    @SerializedName("Mensaje") val message: String?,
)