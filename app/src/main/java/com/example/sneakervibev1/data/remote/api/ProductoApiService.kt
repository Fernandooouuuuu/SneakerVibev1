package com.example.sneakervibev1.data.remote.api


import com.example.sneakervibev1.data.remote.dto.ProductoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoApiService {

    @GET("api/productos")
    suspend fun getProductos(): List<ProductoDto>

    @GET("api/productos/{id}")
    suspend fun getProductoPorId(
        @Path("id") id: Long
    ): ProductoDto
}