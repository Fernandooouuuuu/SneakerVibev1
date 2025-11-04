package com.example.sneakervibev1.navegacion;


sealed class AppVistas(val route:String) {
    object Index : AppVistas("Index")
    object Carro : AppVistas("Carro")
    object Admin : AppVistas("Admin")
    object Login : AppVistas("Login")
    object Nosotros : AppVistas("Nosotros")
    object Productos : AppVistas("Productos")
    object Carga : AppVistas("Carga")
    object Registro : AppVistas("Registro")

}