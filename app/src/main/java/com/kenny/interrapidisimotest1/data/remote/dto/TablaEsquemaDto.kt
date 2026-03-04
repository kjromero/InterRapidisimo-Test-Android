package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TablaEsquemaDto(
    @SerializedName("NombreTabla") val tableName: String?,
    @SerializedName("Descripcion") val description: String?,
    @SerializedName("CamposClave") val keyFields: String?,
    @SerializedName("Activo") val active: Boolean?,
    @SerializedName("NombreServidor") val serverName: String?,
    @SerializedName("NombreBase") val databaseName: String?,
)