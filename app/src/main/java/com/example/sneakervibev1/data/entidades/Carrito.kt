package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "carrito",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id_usuario"],
            childColumns = ["id_usuario"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Carrito(
    @PrimaryKey(autoGenerate = true) val id_carrito: Int = 0,
    val id_usuario: Int,
    val creado_en: String
)