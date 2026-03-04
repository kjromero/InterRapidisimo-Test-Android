package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthRequestDto(
    @SerializedName("Mac") val mac: String = "",
    @SerializedName("NomAplicacion") val appName: String = "Controller APP",
    @SerializedName("Password") val password: String = "SW50ZXIyMDIx\n",
    @SerializedName("Path") val path: String = "",
    @SerializedName("Usuario") val username: String = "cGFtLm1lcmVkeTIx\n",
)