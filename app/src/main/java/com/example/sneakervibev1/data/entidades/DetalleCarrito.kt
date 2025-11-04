package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detalle_carrito",
    foreignKeys = [
        ForeignKey(
            entity = Carrito::class,
            parentColumns = ["id_carrito"],
            childColumns = ["id_carrito"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id_producto"],
            childColumns = ["id_producto"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleCarrito(
    @PrimaryKey(autoGenerate = true) val id_detalle: Int = 0,
    val id_carrito: Int,
    val id_producto: Int,
    val cantidad: Int,
    val subtotal: Double
)
