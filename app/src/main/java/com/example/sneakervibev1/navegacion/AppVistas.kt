package com.example.sneakervibev1.navegacion;


sealed class AppVistas(val route:String) {
    object Index : AppVistas("Index")
    object Carrito : AppVistas("Carrito")
    object Admin : AppVistas("Admin")
    object Login : AppVistas("Login")
    object Nosotros : AppVistas("Nosotros")
    object Productos : AppVistas("Productos?catId={catId}") {
        fun conCategoria(catId: Int) = "Productos?catId=$catId"
    }
    object Carga : AppVistas("Carga")
    object Registro : AppVistas("Registro")

    object Checkout : AppVistas("Checkout")

}