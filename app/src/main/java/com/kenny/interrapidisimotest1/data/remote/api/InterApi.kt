package com.kenny.interrapidisimotest1.data.remote.api

import com.kenny.interrapidisimotest1.data.remote.dto.AuthRequestDto
import com.kenny.interrapidisimotest1.data.remote.dto.AuthResponseDto
import com.kenny.interrapidisimotest1.data.remote.dto.LocalidadDto
import com.kenny.interrapidisimotest1.data.remote.dto.TableSchemeDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface InterApi {

    @GET("apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getVersionControl(): Response<ResponseBody>

    @Headers("usuario: usuario")
    @GET("apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<TableSchemeDto>>

    @GET("apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getPickupLocations(): Response<List<LocalidadDto>>

    @Headers(
        "Usuario: pam.meredy21",
        "Identificacion: 987204545",
        "Accept: text/json",
        "IdUsuario: pam.meredy21",
        "IdCentroServicio: 1295",
        "NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        "IdAplicativoOrigen: 9",
    )
    @POST("FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp")
    suspend fun authenticate(@Body request: AuthRequestDto): Response<AuthResponseDto>
}