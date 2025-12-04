package com.example.sneakervibev1.data.remote.dto

data class ProductoDto(
    val id: Long,
    val nombre: String,
    val marca: String,
    val descripcion: String,
    val categoria: CategoriaDto,
    val variantes: List<DetalleProductoDto>
)

data class CategoriaDto(
    val id: Long,
    val nombreCategoria: String // o 'nombre', según cómo esté en tu API
)

data class DetalleProductoDto(
    val id: Long,
    val talla: String,
    val color: String,
    val imgSrc: String,
    val altText: String?,
    val href: String?,
    val precio: Double,
    val stock: Int
)