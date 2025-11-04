package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id_usuario: Int = 0,
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String = "cliente",
    val activo: Boolean = true
)

