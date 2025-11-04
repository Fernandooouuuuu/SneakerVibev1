package com.example.sneakervibev1.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sneakervibev1.data.entidades.Carrito
import com.example.sneakervibev1.data.entidades.DetalleCarrito
@Dao
interface CarritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun crearCarrito(carrito: Carrito): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarDetalle(detalle: DetalleCarrito)

    @Query("SELECT * FROM detalle_carrito WHERE id_carrito = :idCarrito")
    suspend fun obtenerDetalles(idCarrito: Int): List<DetalleCarrito>

    @Query("DELETE FROM detalle_carrito WHERE id_detalle = :idDetalle")
    suspend fun eliminarDetalle(idDetalle: Int)
}