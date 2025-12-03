package com.example.sneakervibev1.vistas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.R
import com.example.sneakervibev1.data.AppDatabaseInstance
import com.example.sneakervibev1.data.entidades.Producto
import com.example.sneakervibev1.data.repos.ProductoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Productos(navController: NavController) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }

    var showSheet by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errores by remember { mutableStateOf(mapOf<String, String>()) }

    // estado de animación de éxito
    var showSuccess by remember { mutableStateOf(false) }

    // Cargar productos desde Room
    LaunchedEffect(Unit) {
        val db = AppDatabaseInstance.getDatabase(ctx)
        val repo = ProductoRepository(db)
        productos = withContext(Dispatchers.IO) { repo.listar() }
    }

    if (showSheet) {
        ModalBottomSheet(onDismissRequest = { showSheet = false }) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; errores = errores - "nombre" },
                    label = { Text(stringResource(id = R.string.nombre)) },
                    isError = errores["nombre"] != null,
                    supportingText = { errores["nombre"]?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it; errores = errores - "precio" },
                    label = { Text(stringResource(id = R.string.precio_clp)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = errores["precio"] != null,
                    supportingText = { errores["precio"]?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it; errores = errores - "stock" },
                    label = { Text(stringResource(id = R.string.stock)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = errores["stock"] != null,
                    supportingText = { errores["stock"]?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { showSheet = false }) {
                        Text(text = stringResource(id = R.string.cancelar))
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        val errs = mutableMapOf<String, String>()
                        val precioNum = precio.toDoubleOrNull()
                        val stockNum = stock.toIntOrNull()
                        if (nombre.isBlank()) errs["nombre"] = "Ingresa un nombre"
                        if (precioNum == null || precioNum <= 0) errs["precio"] = "Precio debe ser > 0"
                        if (stockNum == null || stockNum < 0) errs["stock"] = "Stock debe ser ≥ 0"
                        errores = errs
                        if (errs.isNotEmpty()) return@Button

                        scope.launch {
                            val db = AppDatabaseInstance.getDatabase(ctx)
                            val repo = ProductoRepository(db)

                            // Usa 1 como categoría por defecto para evitar nulls (ajusta si necesitas).
                            val idCategoriaPorDefecto = 1

                            val nuevo = Producto(
                                id_categoria = idCategoriaPorDefecto,
                                nombre_producto = nombre.trim(),
                                descripcion = "Agregado manualmente",
                                precio = precioNum!!,
                                stock = stockNum!!,
                                imagen = null,
                                activo = true
                            )

                            withContext(Dispatchers.IO) { repo.insertar(nuevo) }
                            productos = withContext(Dispatchers.IO) { repo.listar() }

                            nombre = ""; precio = ""; stock = ""; errores = emptyMap()
                            showSheet = false
                            showSuccess = true // 🔔 dispara animación de éxito
                        }
                    }) {
                        Text(text = stringResource(id = R.string.agregar_este_producto))
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSheet = true }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.agregar_producto)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (productos.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productos) { p ->
                        ProductoCardSimple(p)
                    }
                }
            }

            // 🔔 overlay animado de éxito
            SuccessCheckOverlay(
                visible = showSuccess,
                onAutoHide = { showSuccess = false }
            )
        }
    }
}

@Composable
private fun ProductoCardSimple(p: Producto) {
    val nf = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("CLP")
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(text = p.nombre_producto, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = nf.format(p.precio), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(2.dp))
            Text(text = "Stock: ${p.stock}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SuccessCheckOverlay(
    visible: Boolean,
    onAutoHide: () -> Unit
) {
    // Ocúltalo automáticamente después de ~1.2s
    LaunchedEffect(visible) {
        if (visible) {
            kotlinx.coroutines.delay(1200)
            onAutoHide()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(220)) + scaleIn(initialScale = 0.8f, animationSpec = tween(5000)),
        exit  = fadeOut(animationSpec = tween(180)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = Color(0xFF2E7D32), // verde éxito
                contentColor = Color.White,
                shape = CircleShape,
                tonalElevation = 6.dp,
                shadowElevation = 6.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text("Producto agregado", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
