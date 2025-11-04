package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categoria",
    indices = [Index(value = ["nombre_categorias"], unique = true)]
)


data class Categoria(
    @PrimaryKey(autoGenerate = true) val id_categoria: Int = 0,
    val nombre_categoria: String
)

