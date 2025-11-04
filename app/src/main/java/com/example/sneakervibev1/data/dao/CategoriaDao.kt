package com.example.sneakervibev1.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sneakervibev1.data.entidades.Categoria


@Dao
interface CategoriaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(categoria: Categoria): Long

    @Query("SELECT * FROM categoria ORDER BY nombre_categoria")
    suspend fun todas(): List<Categoria>

    @Query("SELECT * FROM categoria WHERE nombre_categoria = :nombre LIMIT 1")
    suspend fun porNombre(nombre: String): Categoria?
}
