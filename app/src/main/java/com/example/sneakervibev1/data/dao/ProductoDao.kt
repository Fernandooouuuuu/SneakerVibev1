package com.example.sneakervibev1.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sneakervibev1.data.entidades.Producto


@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(producto: Producto)

    @Query("SELECT * FROM producto ORDER BY id_producto DESC")
    suspend fun listar(): List<Producto>

    @Query("SELECT * FROM producto WHERE id_categoria = :idCat ORDER BY id_producto DESC")
    suspend fun listarPorCategoria(idCat: Int): List<Producto>

    @Query("UPDATE producto SET stock = :nuevo WHERE id_producto = :id")
    suspend fun actualizarStock(id: Int, nuevo: Int)
}