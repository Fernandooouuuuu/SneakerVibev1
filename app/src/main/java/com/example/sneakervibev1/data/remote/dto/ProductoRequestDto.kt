package com.example.sneakervibev1.data.remote.dto

data class CategoriaRefDto(
    val id: Long
)

data class VarianteRequestDto(
    val talla: String,
    val color: String,
    val precio: Double,
    val stock: Int,
    val imgSrc: String,
    val href: String,
    val altText: String
)

data class ProductoRequestDto(
    val nombre: String,
    val descripcion: String,
    val marca: String,
    val categoria: CategoriaRefDto,
    val variantes: List<VarianteRequestDto>
)