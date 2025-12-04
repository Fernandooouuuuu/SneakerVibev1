package com.example.sneakervibev1.data.repository

import com.example.sneakervibev1.data.remote.RetrofitClient
import com.example.sneakervibev1.data.remote.dto.LoginRequest
import com.example.sneakervibev1.data.remote.dto.LoginResponse
import com.example.sneakervibev1.data.remote.dto.UsuarioDto

class UsuarioRepository {

    private val api = RetrofitClient.usuarioApi

    suspend fun obtenerUsuario(): List<UsuarioDto> {
        return api.getUsuarios()
    }

    // LOGIN: busca en la lista de usuarios uno que coincida
    suspend fun login(email: String, password: String): UsuarioDto? {
        return try {
            val usuarios = api.getUsuarios()

            // OJO: cambia 'correo' y 'contrasena' por los nombres reales de tu DTO
            usuarios.firstOrNull { user ->
                user.email.equals(email, ignoreCase = true) &&
                        user.contrasena == password
            }
        } catch (e: Exception) {
            null
        }
    }
}
