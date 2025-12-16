package com.example.sneakervibev1.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sneakervibev1.R
import com.example.sneakervibev1.data.SesionUsuario
import com.example.sneakervibev1.navegacion.AppVistas
import com.example.sneakervibev1.ui.model.CategoriaItem
import com.example.sneakervibev1.ui.usuarios.UsuariosViewModel

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import coil.compose.AsyncImage

@Composable
fun Index(navController: NavController, viewModel: UsuariosViewModel = viewModel()) {

    val usuarios = viewModel.usuarios.observeAsState(emptyList())
    val error = viewModel.error.observeAsState()

    // ⚙️ Saber si el usuario logueado es admin
    val esAdmin = SesionUsuario.usuarioActual?.esAdmin == true

    // Llamar a la API una vez al entrar
    LaunchedEffect(Unit) {
        viewModel.cargarUsuarios()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Inicio")
        Spacer(modifier = Modifier.padding(top = 10.dp))

        // Pasamos esAdmin para controlar el menú hamburguesa
        formularioIndex(navController, esAdmin)

        Spacer(modifier = Modifier.height(16.dp))

        // Solo el admin ve el panel simple de usuarios aquí
        if (esAdmin) {
            error.value?.let { msg ->
                Text(text = "Error: $msg")
            }

            if (usuarios.value.isNotEmpty()) {
                Text("Usuarios desde la API:")
                usuarios.value.forEach {
                    Text("- ${it.nombre} ${it.apellido} (${it.email})")
                }
            }
        }
    }
}

@Composable
fun Navbar(navController: NavController, esAdmin: Boolean) {
    var textoBusqueda by remember { mutableStateOf("") }
    var menuOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // 🔎 Buscador
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            placeholder = { Text("Buscar Producto") },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.Red
            )
        )

        Spacer(Modifier.width(10.dp))

        // 🍔 Menú hamburguesa SOLO si es admin
        if (esAdmin) {
            Box {
                IconButton(
                    onClick = { menuOpen = true },
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú administrador",
                        tint = Color.Black
                    )
                }

                DropdownMenu(
                    expanded = menuOpen,
                    onDismissRequest = { menuOpen = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Agregar producto") },
                        onClick = {
                            menuOpen = false
                            navController.navigate(AppVistas.Admin.route)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Panel de usuarios") },
                        onClick = {
                            menuOpen = false
                            // 🔁 Ruta para el panel de usuarios (ajusta en tu NavHost)
                            navController.navigate("adminUsuarios")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Ver compras") },
                        onClick = {
                            menuOpen = false
                            navController.navigate("adminCompras")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarruselImagenes() {
    val imagenes = listOf(
        R.drawable.banner1,
        R.drawable.banner_adidas
    )
    val pagerState = rememberPagerState(pageCount = { imagenes.size })

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)

        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                AsyncImage(
                    model = imagenes[page],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Dots abajo al centro
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(imagenes.size) { i ->
                    val selected = pagerState.currentPage == i
                    Box(
                        modifier = Modifier
                            .size(if (selected) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (selected) Color(0xFFFF2D7A) else Color(0x66FFFFFF))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
    }
}
@Composable
fun ProductosDestacados(navController: NavController) {
    val destacados = listOf(
        Pair("Nike sportwear poleron", R.drawable.nike_sportswear_club_poleron),
        Pair("Nike Air Force 1", R.drawable.airforce1_r1),
        Pair("Adidas Campus 00", R.drawable.campus00s_r1)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Destacados",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(destacados) { (nombre, img) ->

                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .clickable {
                            navController.navigate(AppVistas.Productos.route)
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = img),
                            contentDescription = nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        )
                        Text(
                            text = nombre,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Categorias(
    navController: NavController
) {
    val categorias = listOf(
        CategoriaItem("Ropa", R.drawable.categoria_ropa),
        CategoriaItem("Accesorios", R.drawable.categoria_accesorios),
        CategoriaItem("Zapatillas", R.drawable.categoria_zapatillas)
    )

    fun catId(nombre: String) = when (nombre) {
        "Zapatillas" -> 1
        "Ropa" -> 2
        "Accesorios" -> 3
        else -> -1
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Categorias",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.Start)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(categorias) { cat ->
                CategoriasCard(
                    categoria = cat,
                    onClick = {
                        navController.navigate(AppVistas.Productos.conCategoria(catId(cat.nombre)))
                    }
                )
            }
        }
    }
}

@Composable
fun CategoriasCard(
    categoria: CategoriaItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(400.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = categoria.imagenRes),
                contentDescription = categoria.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(Alignment.CenterStart)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xB3000000))
                        )
                    )
            )

            Text(
                text = categoria.nombre,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun formularioIndex(navController: NavController, esAdmin: Boolean) {
    Navbar(navController, esAdmin)
    Spacer(modifier = Modifier.padding(top = 20.dp))
    CarruselImagenes()
    Spacer(modifier = Modifier.padding(top = 10.dp))
    ProductosDestacados(navController)
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Categorias(navController)
    Spacer(modifier = Modifier.padding(top = 20.dp))


}

@Composable
fun bodyComponenteIndex(
    modifier: Modifier,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Inicio")
        Spacer(modifier = Modifier.padding(top = 10.dp))
        // aquí podrías también pasar esAdmin si lo necesitas
        formularioIndex(navController, esAdmin = false)
    }
}

