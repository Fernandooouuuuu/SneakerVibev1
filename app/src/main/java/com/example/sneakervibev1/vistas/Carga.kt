package com.example.sneakervibev1.vistas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.navegacion.AppVistas
import kotlinx.coroutines.delay

@Composable
fun Carga(navController: NavController){
    LaunchedEffect(Unit) {
        delay(3000) //segundos
        navController.navigate(AppVistas.Index.route){
            popUpTo(AppVistas.Login.route){ inclusive = true }
            launchSingleTop = true
        }
    }

    Box(Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){ CircularProgressIndicator(
            strokeWidth = 6.dp,
            color = Color.Gray
        )
            Spacer(Modifier.height(12.dp))
            Text("Verificando...", style = MaterialTheme.typography.bodyMedium)
        }
    }
}