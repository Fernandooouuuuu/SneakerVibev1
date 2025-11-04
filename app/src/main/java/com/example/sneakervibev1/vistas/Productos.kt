package com.example.sneakervibev1.vistas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.navegacion.AppVistas


@Composable
fun Productos(navController: NavController){
    MaterialTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize()
        ){ innerPadding ->
            BodyComponenteProductos(
                modifier = Modifier.padding(innerPadding),
                navController
            )
        }
    }
}

@Composable
fun formularioProcdutos(){

}



@Composable
fun BodyComponenteProductos(
    modifier: Modifier,
    navController: NavController
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Productos")
        Spacer(modifier = Modifier.padding(top = 32.dp))

        formularioProcdutos()
    }
}
