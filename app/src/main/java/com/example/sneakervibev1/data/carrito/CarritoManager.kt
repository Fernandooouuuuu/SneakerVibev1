package com.example.sneakervibev1.data.carrito

import com.example.sneakervibev1.data.entidades.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CarritoManager {

    data class ItemCarrito(
        val producto: Producto,
        val cantidad: Int
    )

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    fun agregarProducto(producto: Producto) {
        val actual = _items.value.toMutableList()
        val idx = actual.indexOfFirst { it.producto.id_producto == producto.id_producto }

        if (idx >= 0) {
            val viejo = actual[idx]
            actual[idx] = viejo.copy(cantidad = viejo.cantidad + 1)
        } else {
            actual.add(ItemCarrito(producto, 1))
        }

        _items.value = actual
    }

    fun eliminarProducto(idProducto: Int) {
        _items.value = _items.value.filterNot { it.producto.id_producto == idProducto }
    }

    fun limpiar() {
        _items.value = emptyList()
    }

    fun total(): Double =
        _items.value.sumOf { it.producto.precio * it.cantidad }
}