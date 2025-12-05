package com.example.sneakervibev1.data.compras

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ComprasManager {

    data class Compra(
        val id: Int,
        val total: Double,
        val fechaMillis: Long
    )

    private val _compras = MutableStateFlow<List<Compra>>(emptyList())
    val compras: StateFlow<List<Compra>> = _compras

    fun registrarCompra(total: Double) {
        val nuevoId = (_compras.value.maxOfOrNull { it.id } ?: 0) + 1
        val nueva = Compra(
            id = nuevoId,
            total = total,
            fechaMillis = System.currentTimeMillis()
        )
        _compras.value = _compras.value + nueva
    }

    fun limpiar() {
        _compras.value = emptyList()
    }
}