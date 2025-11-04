package com.example.sneakervibev1.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sneakervibev1.data.entidades.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE correo = :correo AND password = :password LIMIT 1")
    suspend fun login(correo: String, password: String): Usuario?

    @Query("SELECT * FROM usuario")
    suspend fun obtenerUsuarios(): List<Usuario>
}