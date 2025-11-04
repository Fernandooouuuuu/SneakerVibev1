package com.example.sneakervibev1.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sneakervibev1.navegacion.AppVistas
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sneakervibev1.ui.componentes.BarraInferior
import com.example.sneakervibev1.vistas.Login
import com.example.sneakervibev1.vistas.Index
import com.example.sneakervibev1.vistas.Productos
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sneakervibev1.vistas.Carga
import com.example.sneakervibev1.vistas.Nosotros
import com.example.sneakervibev1.vistas.Registro

@Composable
fun AppNavegador(){
    val navController = rememberNavController() // Variable declarada en minúsculas

    val navBlackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBlackStackEntry?.destination?.route

    //muestra barra menos en login
    val showBottonBar = currentRoute != AppVistas.Login.route && currentRoute != AppVistas.Carga.route


    Scaffold (

        bottomBar = {
            if (showBottonBar){
                BarraInferior(navController)
            }
        }

    ){ inner ->
        NavHost(
            navController = navController,
            startDestination = AppVistas.Index.route,
            modifier = Modifier.padding(inner)
        ) {
            composable(AppVistas.Login.route) {Login(navController)}
            composable(AppVistas.Registro.route)  { Registro(navController) }
            composable(AppVistas.Index.route) {Index(navController)}
            composable(AppVistas.Productos.route) { Productos(navController) }
            composable(AppVistas.Nosotros.route) { Nosotros(navController) }
            composable(AppVistas.Carga.route){ Carga(navController) }
        }
    }
}