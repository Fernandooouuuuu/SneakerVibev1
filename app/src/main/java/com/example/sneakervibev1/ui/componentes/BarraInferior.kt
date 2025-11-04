package com.example.sneakervibev1.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sneakervibev1.navegacion.AppVistas

@Composable
fun BarraInferior(navController: NavController){

    //orden
    val items = listOf(
        BottonNavItem(AppVistas.Index.route, "Inicio", Icons.Filled.Home),
        BottonNavItem(AppVistas.Productos.route, "Productos", Icons.Filled.Star),
        BottonNavItem(AppVistas.Carro.route,"Carrito", Icons.Filled.ShoppingCart),
        BottonNavItem(AppVistas.Nosotros.route, "Nosotros", Icons.Filled.Info),
        BottonNavItem(AppVistas.Login.route, "Login", Icons.Filled.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = Color.White,
        contentColor = Color.Black
    ){
        items.forEach { item ->
            val selected  = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){ saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label)},
                label = { Text(item.label)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFEB0000),
                    selectedTextColor = Color(0xFFEB0000),
                    indicatorColor = Color(0x14EB0000)
                )
            )

        }
    }
}