package com.kenny.interrapidisimotest1.data.remote.api

import com.kenny.interrapidisimotest1.data.remote.dto.AuthRequestDto
import com.kenny.interrapidisimotest1.data.remote.dto.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SecurityApi {

    @Headers(
        "Usuario: pam.meredy21",
        "Identificacion: 987204545",
        "Accept: text/json",
        "IdUsuario: pam.meredy21",
        "IdCentroServicio: 1295",
        "NombreCentroServicio: PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
        "IdAplicativoOrigen: 9",
    )
    @POST("api/Seguridad/AuthenticaUsuarioApp")
    suspend fun authenticate(@Body request: AuthRequestDto): Response<AuthResponseDto>
}