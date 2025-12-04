package com.example.sneakervibev1.data.remote.dto

data class UsuarioDto (
    val id: Long,
    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val direccion: String?,
    val nroDomicio: String?,   // mismo nombre que en la API
    val region: String?,
    val comuna: String?,
    val esAdmin: Boolean
){
}