package com.example.sneakervibev1.vistas

import android.R
import android.text.TextWatcher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun Nosotros(navController: NavController) {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { inner ->
            BodyComponenteNosotros(
                modifier = Modifier.padding(inner),
                navController = navController
            )
        }
    }
}

@Composable
fun HeaderNosotros(
    mision: String,
    vision: String
){
     Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            CardTextoPequena("Mision", mision)
            CardTextoPequena("Vision", vision)
        }
    }


@Composable
fun CardTextoPequena(titulo: String, texto: String){
    Card (
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
    ){
        Column (Modifier.padding(12.dp)) {
            Text(titulo, style = MaterialTheme.typography.labelLarge, color = Color(0xFF6A1B9A) )
            Spacer(Modifier.height(4.dp))
            Text(texto, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
fun SeccionTitulo(texto: String){
    Text(
        texto,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun TarjetaTexto(texto: String){
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ){
        Text(
            texto,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(14.dp))
    }
}

@Composable
fun ContactoCard(email: String, telefono: String, rrss: String) {
    Card(shape = RoundedCornerShape(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = null, tint = Color(0xFF6A1B9A))
                Spacer(Modifier.width(8.dp))
                Text(email)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Call, contentDescription = null, tint = Color(0xFF6A1B9A))
                Spacer(Modifier.width(8.dp))
                Text(telefono)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF6A1B9A))
                Spacer(Modifier.width(8.dp))
                Text(rrss)
            }
        }
    }
}
@Composable
    fun formularioNosotros(navController: NavController) {
        // Misión / Visión
        HeaderNosotros(
            mision = "Acercar la cultura sneaker con curaduría y precios justos.",
            vision = "Ser la tienda urbana referente en servicio, calidad y experiencia en Chile."
        )

        Spacer(Modifier.height(16.dp))

        // Historia
        SeccionTitulo("Historia")
        TarjetaTexto(
            "SneakerVibe nació en 2025 como un proyecto de pasión por las zapatillas. " +
                    "Partimos con lanzamientos limitados y hoy ampliamos a ropa y accesorios, " +
                    "manteniendo la esencia urbana."
        )

        Spacer(Modifier.height(16.dp))

        // Equipo (solo texto por ahora)
        SeccionTitulo("Equipo")
        TarjetaTexto(
            "Nuestro equipo está compuesto por apasionados del streetwear, " +
                    "dedicados a ofrecer la mejor experiencia de compra y servicio personalizado."
        )

        Spacer(Modifier.height(16.dp))

        // Contacto
        SeccionTitulo("Contacto")
        ContactoCard(
            email = "contacto@sneakervibe.cl",
            telefono = "+56 9 1234 5678",
            rrss = "@sneakervibe"
        )
    }

    @Composable
    fun BodyComponenteNosotros(
        modifier: Modifier,
        navController: NavController
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Quiénes somos",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            formularioNosotros(navController)
            Spacer(Modifier.height(24.dp))
        }
    }