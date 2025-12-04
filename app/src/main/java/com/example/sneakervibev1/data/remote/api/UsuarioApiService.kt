package com.example.sneakervibev1.data.remote.api

import com.example.sneakervibev1.data.remote.dto.LoginRequest
import com.example.sneakervibev1.data.remote.dto.LoginResponse
import com.example.sneakervibev1.data.remote.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApiService {


    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
    @GET("api/usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>

    @GET("api/usuarios/{id}")
    suspend fun getUsuarioPorId(
        @Path("id") id: Long
    ): UsuarioDto

    @POST("api/usuarios")
    suspend fun crearUsuario(
        @Body usuario: UsuarioDto
    ): UsuarioDto
}