package com.example.sneakervibev1.data.remote.api


import com.example.sneakervibev1.data.remote.dto.ProductoDto
import com.example.sneakervibev1.data.remote.dto.ProductoRequestDto
import retrofit2.http.*


interface ProductoApiService {

    @GET("api/productos")
    suspend fun listarProductos(): List<ProductoDto>

    @POST("api/productos")
    suspend fun crearProducto(@Body producto: ProductoRequestDto): ProductoDto


    @DELETE("api/productos/{id}")
    suspend fun eliminarProducto(@Path("id") id: Long)
}