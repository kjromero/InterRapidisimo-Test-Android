package com.kenny.interrapidisimotest1.data.remote.api

import com.kenny.interrapidisimotest1.data.remote.dto.LocalidadDto
import com.kenny.interrapidisimotest1.data.remote.dto.ParametroDto
import com.kenny.interrapidisimotest1.data.remote.dto.TablaEsquemaDto
import retrofit2.Response
import retrofit2.http.GET

interface FrameworkApi {

    @GET("api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersionControl(): Response<ParametroDto>

    @GET("api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<TablaEsquemaDto>>

    @GET("api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getPickupLocations(): Response<List<LocalidadDto>>
}