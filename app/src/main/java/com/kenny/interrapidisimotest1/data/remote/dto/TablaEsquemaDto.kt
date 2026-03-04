package com.kenny.interrapidisimotest1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TablaEsquemaDto(
    @SerializedName("NombreTabla") val tableName: String?,
    @SerializedName("Pk") val primaryKey: String?,
    @SerializedName("QueryCreacion") val createQuery: String?,
    @SerializedName("BatchSize") val batchSize: Int?,
    @SerializedName("Filtro") val filter: String?,
    @SerializedName("Error") val error: String?,
    @SerializedName("NumeroCampos") val fieldCount: Int?,
    @SerializedName("MetodoApp") val appMethod: String?,
    @SerializedName("FechaActualizacionSincro") val lastSyncDate: String?,
)