package com.example.sneakervibev1.data.remote.api

import com.example.sneakervibev1.data.remote.dto.CrearBoletaRequest
import retrofit2.Response
import retrofit2.http.*

interface BoletaApi {

    @POST("api/boleta")
    suspend fun crearBoleta(@Body req: CrearBoletaRequest): Response<Unit>
}
