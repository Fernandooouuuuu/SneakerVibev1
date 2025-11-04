package com.example.sneakervibev1.vistas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.data.AppDatabaseInstance
import com.example.sneakervibev1.data.entidades.Categoria
import com.example.sneakervibev1.data.entidades.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.sneakervibev1.data.dao.ProductoDao
import com.example.sneakervibev1.data.dao.CategoriaDao


@Composable
fun Admin(navController: NavController) {
    MaterialTheme {
        Scaffold { inner ->
            BodyComponenteAdmin(Modifier.padding(inner), navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyComponenteAdmin(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { AppDatabaseInstance.getDatabase(context) }
    val catDao: CategoriaDao = remember { db.categoriaDao() }
    val prodDao: ProductoDao = remember { db.productoDao() }

    // Estado del formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioTxt by remember { mutableStateOf("") }
    var stockTxt by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    // Categorías
    var categorias by remember { mutableStateOf(listOf<Categoria>()) }
    var catSeleccionada by remember { mutableStateOf<Categoria?>(null) }
    var menuCatOpen by remember { mutableStateOf(false) }

    // Precarga categorías (solo si no hay)
    LaunchedEffect(Unit) {
        val existentes = catDao.todas()
        if (existentes.isEmpty()) {
            catDao.insertar(Categoria(nombre_categoria = "Zapatillas"))
            catDao.insertar(Categoria(nombre_categoria = "Ropa"))
            catDao.insertar(Categoria(nombre_categoria = "Accesorios"))
        }
        categorias = catDao.todas()
        catSeleccionada = categorias.firstOrNull()
    }

    fun formOk(): Boolean {
        val p = precioTxt.toDoubleOrNull()
        val s = stockTxt.toIntOrNull()
        return nombre.isNotBlank() && (p != null && p > 0) && (s != null && s >= 0) && catSeleccionada != null
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear producto", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = nombre, onValueChange = { nombre = it },
            label = { Text("Nombre") }, singleLine = true, modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion, onValueChange = { descripcion = it },
            label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precioTxt, onValueChange = { precioTxt = it },
            label = { Text("Precio") }, singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stockTxt, onValueChange = { stockTxt = it },
            label = { Text("Stock") }, singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imagen, onValueChange = { imagen = it },
            label = { Text("Imagen (URL o ruta)") },
            singleLine = true, modifier = Modifier.fillMaxWidth()
        )

        // Selector de categoría
        ExposedDropdownMenuBox(
            expanded = menuCatOpen,
            onExpandedChange = { menuCatOpen = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                readOnly = true,
                value = catSeleccionada?.nombre_categoria ?: "Selecciona categoría",
                onValueChange = {},
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuCatOpen) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuCatOpen,
                onDismissRequest = { menuCatOpen = false }
            ) {
                categorias.forEach { c ->
                    DropdownMenuItem(
                        text = { Text(c.nombre_categoria) },
                        onClick = {
                            catSeleccionada = c
                            menuCatOpen = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                if (!formOk()) {
                    Toast.makeText(context, "Completa bien el formulario", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val p = precioTxt.toDouble()
                val s = stockTxt.toInt()
                scope.launch(Dispatchers.IO) {
                    prodDao.insertar(
                        Producto(
                            id_categoria = catSeleccionada!!.id_categoria,
                            nombre_producto = nombre,
                            descripcion = descripcion,
                            precio = p,
                            stock = s,
                            imagen = imagen.ifBlank { null }
                        )
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Producto creado", Toast.LENGTH_SHORT).show()
                        // Limpia el formulario
                        nombre = ""; descripcion = ""; precioTxt = ""; stockTxt = ""; imagen = ""
                    }
                }
            },
            enabled = formOk(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar producto")
        }
    }
}
