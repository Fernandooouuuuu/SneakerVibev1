package com.example.sneakervibev1.data.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.sneakervibev1.data.remote.dto.ProductoDto  // 👈 IMPORTANTE

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

/**
 * Mapper desde el DTO de la API → entidad Producto de la app
 */
fun ProductoDto.toProducto(): Producto {        // 👈 EXTENSIÓN SOBRE ProductoDto
    val v = variantes.firstOrNull()

    return Producto(
        id_producto = id.toInt(),
        id_categoria = categoria.id.toInt(),
        nombre_producto = nombre,
        descripcion = descripcion,
        precio = v?.precio ?: 0.0,
        stock = v?.stock ?: 0,
        imagen = v?.imgSrc,
        activo = true
    )
}
