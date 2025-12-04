package com.example.sneakervibev1.data.repository

import com.example.sneakervibev1.data.entidades.Producto
import com.example.sneakervibev1.data.remote.RetrofitClientProductos


class ProductoRepository {private val api = RetrofitClientProductos.api

    suspend fun listarProductos(): List<Producto> {
        return try {
            api.getProductos()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerProducto(id: Long): Producto? {
        return try {
            api.getProductoPorId(id)
        } catch (e: Exception) {
            null
        }
    }
}