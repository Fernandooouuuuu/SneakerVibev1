package com.example.sneakervibev1.vistas

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.R
import com.example.sneakervibev1.navegacion.AppVistas
import com.example.sneakervibev1.ui.model.CategoriaItem
import java.nio.file.WatchEvent


@Composable
fun Index(navController: NavController){
    MaterialTheme {
        Scaffold (
            modifier = Modifier.fillMaxSize()
        ){ innerPadding ->
            bodyComponenteIndex(
                modifier = Modifier.padding(innerPadding),
                navController
            )
        }
    }
}

@Composable
fun Navbar(navController: NavController){
    var textoBusqueda by remember { mutableStateOf("") }
    var menuOpen by remember { mutableStateOf(false) }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        //buscador
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it},
            placeholder = { Text("Buscar Producto")},
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

        //desplegable
        Box{
            IconButton(
                onClick = { menuOpen = true },
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = menuOpen,
                onDismissRequest = { menuOpen = false }
            ) {
                DropdownMenuItem(
                    text = {Text("Agregar producto" ) },
                    onClick = {
                        menuOpen = false
                        navController.navigate(AppVistas.Admin.route)
                    }
                )
            }
        }
    }
}



@Composable
fun Categorias(
    onCategoriaClick: (CategoriaItem) -> Unit = {}
){
    val categorias = listOf(
        CategoriaItem("Ropa", R.drawable.categoria_ropa),
        CategoriaItem("Accesorios", R.drawable.categoria_accesorios),
        CategoriaItem("Zapatillas", R.drawable.categoria_zapatillas)
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Categorias",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ){
            items(categorias){ cat ->
                CategoriasCard(
                    categoria = cat,
                    onClick = {onCategoriaClick(cat)}
                )
            }
        }
    }
}

@Composable
fun CategoriasCard(
    categoria : CategoriaItem,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(400.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(Modifier.fillMaxSize()){
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
fun formularioIndex(navController: NavController){
    Navbar(navController)
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Categorias()
    Spacer(modifier = Modifier.padding(top = 20.dp))
}

@Composable
fun bodyComponenteIndex(
    modifier: Modifier,
    navController: NavController
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Inicio")
        Spacer(modifier = Modifier.padding(top = 10.dp))
        formularioIndex(navController)
        }
}


