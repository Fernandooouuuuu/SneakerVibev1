package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto",
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id_categoria"],
            childColumns = ["id_categoria"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("id_categoria")]
)
data class Producto(
    @PrimaryKey(autoGenerate = true) val id_producto: Int = 0,
    val id_categoria: Int,
    val nombre_producto: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val imagen: String? = null,
    val activo: Boolean = true
)
