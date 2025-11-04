package com.example.sneakervibev1.data

import com.example.sneakervibev1.data.entidades.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder{
    fun seed(db: SneakerVibeDB){
        val usuarioDao = db.usuarioDao()
        CoroutineScope(Dispatchers.IO).launch {
            if (usuarioDao.obtenerUsuarios().isEmpty()){
                usuarioDao.insertarUsuario(
                    Usuario(
                        nombre = "Admin",
                        correo = "admin@gmail.com",
                        password = "12345",
                        rol = "admin"
                    )
                )
                usuarioDao.insertarUsuario(
                    Usuario(
                        nombre = "Fernando",
                        correo = "fernando@gmail.com",
                        password = "12345",
                        rol = "cliente"
                    )
                )

                usuarioDao.insertarUsuario(
                    Usuario(
                        nombre = "Matias",
                        correo = "matias@gmail.com",
                        password = "12345",
                        rol = "cliente"
                    )
                )
            }
        }
    }
}