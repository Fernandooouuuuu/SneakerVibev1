package com.example.sneakervibev1.vistas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sneakervibev1.data.AppDatabaseInstance
import com.example.sneakervibev1.data.entidades.Usuario
import com.example.sneakervibev1.navegacion.AppVistas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun Registro(navController: NavController) {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { inner ->
            bodyComponenteRegistro(
                modifier = Modifier.padding(inner),
                navController = navController
            )
        }
    }
}



@Composable
fun bodyComponenteRegistro(
    modifier: Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        formularioRegistro(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun formularioRegistro(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val db = remember { AppDatabaseInstance.getDatabase(context) }
    val usuarioDao = remember { db.usuarioDao() }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    var rol by remember { mutableStateOf("cliente") }
    var menuRolOpen by remember { mutableStateOf(false) }
    var activo by remember { mutableStateOf(true) }

    fun isEmailValido(e: String): Boolean =
        e.contains("@") && e.length in 6..100

    fun isPasswordValido(p: String): Boolean =
        p.length in 5..40

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it.trim() },
            label = { Text("Correo") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = correo.isNotEmpty() && !isEmailValido(correo),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPass = !showPass }) {
                    Icon(
                        imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Mostrar u ocultar contraseña"
                    )
                }
            },
            isError = password.isNotEmpty() && !isPasswordValido(password),
            modifier = Modifier.fillMaxWidth()
        )

        // Selector de rol (admin / cliente)
        ExposedDropdownMenuBox(
            expanded = menuRolOpen,
            onExpandedChange = { menuRolOpen = it }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = rol,
                onValueChange = {},
                label = { Text("Rol") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuRolOpen)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuRolOpen,
                onDismissRequest = { menuRolOpen = false }
            ) {
                listOf("cliente", "admin").forEach { r ->
                    DropdownMenuItem(
                        text = { Text(r) },
                        onClick = {
                            rol = r
                            menuRolOpen = false
                        },
                        leadingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                    )
                }
            }
        }

        // Activo (switch)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Activo")
            Switch(checked = activo, onCheckedChange = { activo = it })
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                // Validaciones básicas
                if (nombre.isBlank() || !isEmailValido(correo) || !isPasswordValido(password)) {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                scope.launch(Dispatchers.IO) {
                    // Evitar duplicado por correo (simple usando el DAO que ya tienes)
                    val yaExiste = usuarioDao.obtenerUsuarios().any { it.correo.equals(correo, ignoreCase = true) }
                    if (yaExiste) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Ese correo ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                        return@launch
                    }

                    usuarioDao.insertarUsuario(
                        Usuario(
                            nombre = nombre,
                            correo = correo,
                            password = password,
                            rol = rol,
                            activo = activo
                        )
                    )

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Usuario creado", Toast.LENGTH_SHORT).show()
                        // Vuelve a Login para que pruebe iniciar sesión
                        navController.navigate(AppVistas.Login.route) {
                            launchSingleTop = true
                            popUpTo(AppVistas.Registro.route) { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank() && isEmailValido(correo) && isPasswordValido(password)
        ) {
            Text("Registrar")
        }

        TextButton(
            onClick = {
                navController.navigate(AppVistas.Login.route) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}