package com.example.sneakervibev1.data.remote.dto

data class LoginRequest (
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: Long,
    val nombre: String,
    val email: String,
    val rol: String
){
}