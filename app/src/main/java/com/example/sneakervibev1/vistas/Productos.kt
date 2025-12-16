package com.example.sneakervibev1.vistas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sneakervibev1.R
import com.example.sneakervibev1.data.SesionUsuario
import com.example.sneakervibev1.data.carrito.CarritoManager
import com.example.sneakervibev1.data.entidades.Producto
import com.example.sneakervibev1.data.repository.ProductoRepository
import com.example.sneakervibev1.util.obtenerDrawableProducto
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Productos(navController: NavController, catId: Int) {
    val scope = rememberCoroutineScope()
    val repo = remember { ProductoRepository() }

    var allProductos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var showSheet by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errores by remember { mutableStateOf(mapOf<String, String>()) }
    var showSuccess by remember { mutableStateOf(false) }



    // Imagen seleccionada desde archivos
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val esAdmin = SesionUsuario.usuarioActual?.esAdmin == true

    val snackbarHostState = remember { SnackbarHostState() }

    // Cargar productos
    LaunchedEffect(Unit) {
        try {
            productos = repo.listarProductos()
        } catch (e: Exception) {
            e.printStackTrace()
            productos = emptyList()
        }
    }

    LaunchedEffect(catId, allProductos) {
        productos =
            if (catId == -1) allProductos
            else allProductos.filter { it.id_categoria == catId }
    }
    // Picker de imagen
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri
        errores = errores - "imagen"
    }

    var addedPulse by remember { mutableStateOf(false) }


    // ---------- BOTTOM SHEET (ADMIN) ----------
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

                Spacer(Modifier.height(12.dp))
                Text("Imagen", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        pickImage.launch("image/*")


                    }) {
                        Text(if (imagenUri == null) "Seleccionar" else "Cambiar")
                    }

                    Spacer(Modifier.width(12.dp))

                    if (imagenUri != null) {
                        AsyncImage(
                            model = imagenUri,
                            contentDescription = "Preview",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                errores["imagen"]?.let {
                    Spacer(Modifier.height(6.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                        if (imagenUri == null) errs["imagen"] = "Selecciona una imagen"

                        errores = errs
                        if (errs.isNotEmpty()) return@Button

                        scope.launch {
                            try {
                                repo.crearProducto(
                                    nombre = nombre.trim(),
                                    precio = precioNum!!,
                                    stock = stockNum!!,
                                    imgSrc = imagenUri.toString() // ✅ uri real
                                )

                                allProductos = repo.listarProductos()

                                nombre = ""
                                precio = ""
                                stock = ""
                                imagenUri = null
                                errores = emptyMap()
                                showSheet = false
                                showSuccess = true
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }) {
                        Text(text = stringResource(id = R.string.agregar_este_producto))
                    }
                }
            }
        }
    }

    // ---------- CONTENIDO PRINCIPAL ----------
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if (esAdmin) {
                FloatingActionButton(onClick = { showSheet = true }) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.agregar_producto)
                    )
                }
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
                        ProductoCardSimple(
                            p = p,
                            esAdmin = esAdmin,
                            onAgregarCarrito = {
                                CarritoManager.agregarProducto(p)
                                addedPulse = true
                                scope.launch {
                                    snackbarHostState.showSnackbar("Producto agregado al carrito")
                                }
                            },
                            onDelete = {
                                scope.launch {
                                    try {
                                        repo.eliminarProducto(p.id_producto)
                                        allProductos = repo.listarProductos()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        )
                    }
                }
            }

            SuccessCheckOverlay(
                visible = showSuccess,
                onAutoHide = { showSuccess = false }
            )

            AnimatedVisibility(
                visible = addedPulse,
                enter = fadeIn(tween(150)) + scaleIn(initialScale = 0.7f, animationSpec = tween(250)),
                exit = fadeOut(tween(4000)) + scaleOut(targetScale = 0.7f, animationSpec = tween(500))
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        tonalElevation = 100.dp
                    ) {
                        Text(
                            "✅ Agregado al carrito",
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                        )
                    }
                }
            }

            LaunchedEffect(addedPulse) {
                if (addedPulse) {
                    kotlinx.coroutines.delay(900)
                    addedPulse = false
                }
            }
        }
    }
}


@Composable
private fun ProductoCardSimple(
    p: Producto,
    esAdmin: Boolean,
    onAgregarCarrito: () -> Unit,
    onDelete: () -> Unit
) {
    val ctx = LocalContext.current

    val nf = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("CLP")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ✅ si viene uri (content://) mostrar AsyncImage, si no fallback a drawable
            val img = p.imagen
            if (img != null && img.startsWith("content://")) {
                AsyncImage(
                    model = img,
                    contentDescription = p.nombre_producto,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(end = 12.dp)
                )
            } else {
                val imagenId = obtenerDrawableProducto(ctx, img)
                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = p.nombre_producto,
                    modifier = Modifier
                        .size(90.dp)
                        .padding(end = 12.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = p.nombre_producto, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(text = nf.format(p.precio), style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(2.dp))
                Text(text = "Stock: ${p.stock}", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))

                if (esAdmin) {
                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Eliminar")
                    }
                } else {
                    Button(onClick = onAgregarCarrito, modifier = Modifier.align(Alignment.End)) {
                        Text("Agregar al carrito")
                    }
                }
            }
        }
    }
}

@Composable
private fun SuccessCheckOverlay(
    visible: Boolean,
    onAutoHide: () -> Unit
) {
    LaunchedEffect(visible) {
        if (visible) {
            kotlinx.coroutines.delay(1200)
            onAutoHide()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(220)) + scaleIn(initialScale = 0.8f, animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(180)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                color = Color(0xFF2E7D32),
                contentColor = Color.White,
                shape = CircleShape,
                tonalElevation = 6.dp,
                shadowElevation = 6.dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(36.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Producto agregado", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
