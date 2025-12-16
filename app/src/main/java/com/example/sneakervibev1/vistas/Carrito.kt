package com.example.sneakervibev1.vistas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.data.carrito.CarritoManager
import kotlinx.coroutines.launch

import com.example.sneakervibev1.data.compras.ComprasManager
import com.example.sneakervibev1.navegacion.AppVistas
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrito(navController: NavController) {
    val itemsCarrito by CarritoManager.items.collectAsState()
    val total = CarritoManager.total()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            if (itemsCarrito.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(itemsCarrito) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(item.producto.nombre_producto)
                                    Text("Cantidad: ${item.cantidad}")
                                    Text("Precio c/u: ${item.producto.precio.toInt()} CLP")
                                }

                                IconButton(
                                    onClick = {
                                        CarritoManager.eliminarProducto(item.producto.id_producto)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Eliminar"
                                    )
                                }
                            }
                        }
                    }
                }

                Divider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total: ${total.toInt()} CLP",
                        style = MaterialTheme.typography.titleMedium
                    )
                    val scope = rememberCoroutineScope()
                    val snackbarHostState = remember { SnackbarHostState() }

                    Button(onClick = {
                        scope.launch {
                            // registrar compra local
                            ComprasManager.registrarCompra(total)
                            // limpiar carrito
                            CarritoManager.limpiar()
                            navController.navigate(AppVistas.Checkout.route)
                        }
                    }) {
                        Text("Pagar")
                    }
                }
            }
        }
    }
}
