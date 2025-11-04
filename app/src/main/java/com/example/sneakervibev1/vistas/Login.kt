package com.example.sneakervibev1.vistas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.navegacion.AppVistas
import androidx.compose.ui.text.input.KeyboardType // Importación clave
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.sneakervibev1.R


private fun isEmailValid(email: String): Boolean{
    val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return email.matches(regex) && email.length in 6..100
}

private fun isPassValid(pass: String): Boolean = pass.length in 4..12

@Composable
fun Login(navController: NavController){
    MaterialTheme{
        Scaffold (
            modifier = Modifier.fillMaxSize()
        ){ innerPadding ->
            bodyComponente(
                modifier = Modifier.padding(innerPadding),
                navController
            )
        }
    }
}

@Composable
fun LoginLogo(){
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo de login",
        modifier = Modifier.size(150.dp).padding(bottom = 32.dp)
    )
}

@Composable
fun cargarOutLinedTextFieldPassword() {
    // 1. Estado para almacenar la contraseña
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Favorite Icon"
            )
            Text("         Contraseña") }, // Etiqueta visible

        // 2. Oculta el texto ingresado con puntos (••••)
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun cargarOutLinedTextField(){
    var data by remember { mutableStateOf("") }
        OutlinedTextField(
            value = data,
            onValueChange = {data = it},
            label = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Favorite Icon"
            )
            Text( "         Email")
            }
        )

}



@Composable
fun formulario(navController: NavController){

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var emailTouched by remember { mutableStateOf(false) }
    var passTouched by remember { mutableStateOf(false) }
    val emailError = emailTouched && !isEmailValid(email)
    val passError = passTouched && !isPassValid(pass)
    val formOk = isEmailValid(email) && isPassValid(pass)

    LoginLogo()
    Spacer(modifier = Modifier.padding(top = 20.dp))

    CampoEmail(
        value = email,
        onValueChange = {
            email = it.trim()
            if (!emailTouched) emailTouched = true
        },
        isError = emailError
    )
    Spacer(modifier = Modifier.padding(top = 20.dp))

    CampoPassword(
        value = pass,
        onValueChange = {
            pass = it
            if (!passTouched) passTouched = true
        },
        isError = passError
    )
    Spacer(modifier = Modifier.padding(top = 10.dp))
    recordarBoton()
    Spacer(modifier = Modifier.padding(top = 10.dp))
    cargarBotonLogin(navController)
    Spacer(modifier = Modifier.padding(top = 20.dp))
    OlvidastePassword()
    BotonRegistro(navController)

}

@Composable
fun CampoEmail(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text("Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        isError = isError,
        supportingText = {
            if (isError) Text("Email inválido", color = Color.Red)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    )
}


@Composable
fun CampoPassword(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
){
    var show by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text("Contraseña") },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            TextButton(onClick = { show = !show }) {
                Text(if (show) "Ocultar" else "Ver")
            }
        },
        visualTransformation = if (show) androidx.compose.ui.text.input.VisualTransformation.None
        else androidx.compose.ui.text.input.PasswordVisualTransformation(),
        isError = isError,
        supportingText = {
            if (isError) Text("5 a 40 caracteres", color = Color.Red)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    )
}

@Composable
fun cargarBotonLogin(navController: NavController){
    Button(
        onClick = {
            navController.navigate(AppVistas.Carga.route) {
                popUpTo(AppVistas.Login.route){ inclusive = false}
                launchSingleTop = true
            }
        },
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            contentColor = Color.White
        ),
        modifier = Modifier.width(280.dp)
    ) {
        Text("Ingresar")
    }
}

@Composable
fun recordarBoton(){
    var recordarme by remember { mutableStateOf(false) }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ){
        Switch(
            checked = recordarme,
            onCheckedChange = {recordarme = it},
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF6A1B9A),
                checkedTrackColor = Color(0xFFB39DDB),
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Recordarme")
    }
}

@Composable
fun OlvidastePassword(onClick: () -> Unit = {}){
   Text(
       text = "Olvidaste tu contraseña?",
       color = Color.Gray,
       modifier = Modifier.padding(top = 24.dp).clickable{ onClick()},
       style = MaterialTheme.typography.bodyMedium
   )
}

@Composable
fun BotonRegistro(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(AppVistas.Registro.route) {
                launchSingleTop = true
            }
        },
    ) {
        Text(
            text = "Crear cuenta",
            color = Color.Gray, // mismo color del “Olvidaste tu contraseña?”
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun bodyComponente(
    modifier: Modifier,
    navController: NavController
){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("SneakerVibe")
        Spacer(modifier = Modifier.padding(top = 32.dp))

        formulario(navController)
    }
}