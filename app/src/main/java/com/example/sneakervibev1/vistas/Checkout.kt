package com.example.sneakervibev1.vistas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.data.SesionUsuario
import com.example.sneakervibev1.data.carrito.CarritoManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkout(navController: NavController) {
    val scope = rememberCoroutineScope()

    var direccion by remember { mutableStateOf(SesionUsuario.usuarioActual?.direccion ?: "") }
    var comuna by remember { mutableStateOf(SesionUsuario.usuarioActual?.comuna ?: "") }
    var region by remember { mutableStateOf(SesionUsuario.usuarioActual?.region ?: "") }
    var telefono by remember { mutableStateOf("") }

    var medioPago by remember { mutableStateOf("Tarjeta") } // "Tarjeta" | "Transferencia" | "Efectivo"
    var nroTarjeta by remember { mutableStateOf("") }
    var venc by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    val total = CarritoManager.total()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Checkout") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Datos de envío", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = comuna,
                    onValueChange = { comuna = it },
                    label = { Text("Comuna") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = region,
                    onValueChange = { region = it },
                    label = { Text("Región") },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Divider()

            Text("Medio de pago", style = MaterialTheme.typography.titleMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = medioPago == "Tarjeta",
                    onClick = { medioPago = "Tarjeta" },
                    label = { Text("Tarjeta") }
                )
                FilterChip(
                    selected = medioPago == "Transferencia",
                    onClick = { medioPago = "Transferencia" },
                    label = { Text("Transferencia") }
                )
                FilterChip(
                    selected = medioPago == "Efectivo",
                    onClick = { medioPago = "Efectivo" },
                    label = { Text("Efectivo") }
                )
            }

            if (medioPago == "Tarjeta") {
                OutlinedTextField(
                    value = nroTarjeta,
                    onValueChange = { nroTarjeta = it },
                    label = { Text("N° tarjeta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = venc,
                        onValueChange = { venc = it },
                        label = { Text("Venc. MM/AA") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = { Text("CVV") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        modifier = Modifier.weight(1f)
                    )
                }
            } else if (medioPago == "Transferencia") {
                Text("Te mostraremos los datos de transferencia al confirmar.")
            } else {
                Text("Pago contra entrega.")
            }

            Spacer(Modifier.weight(1f))

            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Text("Total: ${total.toInt()} CLP", style = MaterialTheme.typography.titleLarge)

            Button(
                onClick = {
                    error = null

                    if (direccion.isBlank() || comuna.isBlank() || region.isBlank()) {
                        error = "Completa dirección, comuna y región."
                        return@Button
                    }
                    if (medioPago == "Tarjeta") {
                        if (nroTarjeta.length < 12 || venc.isBlank() || cvv.length < 3) {
                            error = "Completa los datos de tarjeta."
                            return@Button
                        }
                    }

                    loading = true
                    scope.launch {
                        // Aquí después conectamos tu API de boleta (crearBoleta)
                        // Por ahora: simulamos compra hecha
                        CarritoManager.limpiar()
                        loading = false
                        navController.popBackStack() // vuelve
                        // o navega a una pantalla “Compra exitosa”
                    }
                },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (loading) "Procesando..." else "Confirmar y pagar")
            }
        }
    }
}