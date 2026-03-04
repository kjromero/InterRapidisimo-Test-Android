package com.kenny.interrapidisimotest1.data.remote.api

import com.kenny.interrapidisimotest1.data.remote.dto.LocalidadDto
import com.kenny.interrapidisimotest1.data.remote.dto.TablaEsquemaDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface FrameworkApi {

    @GET("api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersionControl(): Response<ResponseBody>

    @Headers(
        "usuario: usuario",
    )
    @GET("api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<TablaEsquemaDto>>

    @GET("api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getPickupLocations(): Response<List<LocalidadDto>>
}