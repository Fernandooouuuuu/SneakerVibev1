package com.example.sneakervibev1.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.data.compras.ComprasManager
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCompras(navController: NavController) {
    val compras = ComprasManager.compras.collectAsState()

    val formato = remember {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "CL"))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compras realizadas") }
            )
        }
    ) { padding ->
        if (compras.value.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                Text(
                    text = "Aún no hay compras",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(compras.value) { compra ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Compra #${compra.id}")
                            Text("Total: ${compra.total.toInt()} CLP")
                            Text("Fecha: ${formato.format(Date(compra.fechaMillis))}")
                        }
                    }
                }
            }
        }
    }
}
