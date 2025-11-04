package com.example.sneakervibev1.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sneakervibev1.data.dao.CarritoDao
import com.example.sneakervibev1.data.dao.ProductoDao
import com.example.sneakervibev1.data.dao.UsuarioDao
import com.example.sneakervibev1.data.entidades.Carrito
import com.example.sneakervibev1.data.entidades.Categoria
import com.example.sneakervibev1.data.entidades.DetalleCarrito
import com.example.sneakervibev1.data.entidades.Producto
import com.example.sneakervibev1.data.entidades.Usuario

@Database(
    entities = [
        com.example.sneakervibev1.data.entidades.Usuario::class,
        com.example.sneakervibev1.data.entidades.Categoria::class,
        com.example.sneakervibev1.data.entidades.Producto::class,
        com.example.sneakervibev1.data.entidades.Carrito::class,
        com.example.sneakervibev1.data.entidades.DetalleCarrito::class
    ],
    version = 1
)

abstract class SneakerVibeDB : RoomDatabase() {
    abstract fun usuarioDao(): com.example.sneakervibev1.data.dao.UsuarioDao
    abstract fun categoriaDao(): com.example.sneakervibev1.data.dao.CategoriaDao
    abstract fun productoDao(): com.example.sneakervibev1.data.dao.ProductoDao
}