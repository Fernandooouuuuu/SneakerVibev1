package com.example.sneakervibev1.data.repository

import com.example.sneakervibev1.data.entidades.Producto
import com.example.sneakervibev1.data.entidades.toProducto   // 👈 IMPORTANTE
import com.example.sneakervibev1.data.remote.RetrofitClientProductos
import com.example.sneakervibev1.data.remote.dto.CategoriaDto
import com.example.sneakervibev1.data.remote.dto.DetalleProductoDto
import com.example.sneakervibev1.data.remote.dto.ProductoDto
import com.example.sneakervibev1.data.remote.dto.ProductoRequestDto
import com.example.sneakervibev1.data.remote.dto.CategoriaRefDto
import com.example.sneakervibev1.data.remote.dto.VarianteRequestDto

class ProductoRepository {

    private val api = RetrofitClientProductos.api

    // 👉 LA API DEVUELVE DTOs, AQUÍ LOS MAPEAMOS A Producto
    suspend fun listarProductos(): List<Producto> {
        val dtos: List<ProductoDto> = api.listarProductos()
        return dtos.map { it.toProducto() }   // it es ProductoDto → usa la extensión
    }

    // 👉 Crear producto en la API y devolverlo ya mapeado a Producto
    suspend fun crearProducto(
        nombre: String,
        precio: Double,
        stock: Int,
        imgSrc: String
    ): Producto {
        val request = ProductoRequestDto(
            nombre = nombre,
            descripcion = "Agregado desde app móvil",
            marca = "SneakerVibe",
            categoria = CategoriaRefDto(id = 1),   // Zapatillas
            variantes = listOf(
                VarianteRequestDto(
                    talla = "UNICA",
                    color = "default",
                    precio = precio,
                    stock = stock,
                    imgSrc = imgSrc,
                    href = "",
                    altText = nombre
                )
            )
        )

        val dto: ProductoDto = api.crearProducto(request)
        return dto.toProducto()    // 👈 AQUÍ TAMBIÉN USAMOS LA EXTENSIÓN
    }

    suspend fun eliminarProducto(idProducto: Int) {
        api.eliminarProducto(idProducto.toLong())
    }
}
