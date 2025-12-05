package com.example.sneakervibev1.data.remote.dto

data class CrearBoletaRequest(
    val usuarioId: Long,
    val total: Double,
    val items: List<BoletaItemRequest>
)

data class BoletaItemRequest(
    val productoId: Long,   // ES EL ID DEL DETALLE PRODUCTO
    val cantidad: Int,
    val precioUnitario: Double
)
