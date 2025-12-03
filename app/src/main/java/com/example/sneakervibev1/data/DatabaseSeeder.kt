package com.example.sneakervibev1.data

import com.example.sneakervibev1.data.entidades.Usuario
import com.example.sneakervibev1.data.entidades.Categoria
import com.example.sneakervibev1.data.entidades.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {
    fun seed(db: SneakerVibeDB) {
        val usuarioDao = db.usuarioDao()
        val categoriaDao = db.categoriaDao()
        val productoDao = db.productoDao()

        CoroutineScope(Dispatchers.IO).launch {
            // 1) Usuarios
            if (usuarioDao.obtenerUsuarios().isEmpty()) {
                usuarioDao.insertarUsuario(
                    Usuario(
                        nombre = "Admin",
                        correo = "admin@gmail.com",
                        password = "admin123",
                        rol = "admin"
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

            // 2) Categorías
            val existentes = categoriaDao.todas()
            val idCatMap = if (existentes.isEmpty()) {
                val base = listOf("Zapatillas", "Poleras", "Pantalones", "Accesorios")
                val map = mutableMapOf<String, Int>()
                base.forEach { nombre ->
                    // insert IGNORE (única por nombre)
                    categoriaDao.insertar(Categoria(nombre_categoria = nombre))
                    val real = categoriaDao.porNombre(nombre)!!
                    map[nombre] = real.id_categoria
                }
                map
            } else {
                existentes.associate { it.nombre_categoria to it.id_categoria }.toMutableMap()
            }

            val idZapas = idCatMap["Zapatillas"] ?: categoriaDao.porNombre("Zapatillas")!!.id_categoria
            val idAcc   = idCatMap["Accesorios"] ?: categoriaDao.porNombre("Accesorios")!!.id_categoria
            val idPoler = idCatMap["Poleras"] ?: categoriaDao.porNombre("Poleras")!!.id_categoria

            // 3) Productos
            if (productoDao.listar().isEmpty()) {
                val seedProductos = listOf(
                    // Zapatillas (Campus)
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Adidas Campus 00s (r1)",
                        descripcion = "Clásicas y urbanas para el día a día.",
                        precio = 99990.0,
                        stock = 12,
                        imagen = "campus00s_r1.png",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Adidas Campus 00s (r2)",
                        descripcion = "Perfil retro, comodidad actual.",
                        precio = 99990.0,
                        stock = 10,
                        imagen = "campus00s_r2.png",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Adidas Campus 00s (r3)",
                        descripcion = "Un must del streetwear.",
                        precio = 99990.0,
                        stock = 8,
                        imagen = "campus00s_r3.png",
                        activo = true
                    ),

                    // Otras zapas
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Nike Dunk",
                        descripcion = "Clásico del basket convertido en ícono urbano.",
                        precio = 109990.0,
                        stock = 9,
                        imagen = "dunk.jpg",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Nike Air Max TN (Negra)",
                        descripcion = "Estilo agresivo y cómodo para la calle.",
                        precio = 129990.0,
                        stock = 7,
                        imagen = "tn_negra.jpg",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Nike Air Max TN (Blanca)",
                        descripcion = "Clásica y limpia para cualquier fit.",
                        precio = 129990.0,
                        stock = 6,
                        imagen = "tn_blanca.jpg",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Nike Air Max TN (Azul)",
                        descripcion = "Colorway llamativo con la misma esencia TN.",
                        precio = 129990.0,
                        stock = 6,
                        imagen = "tn_azul.jpg",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Adidas Superstar",
                        descripcion = "Icono Originals de todos los tiempos.",
                        precio = 89990.0,
                        stock = 15,
                        imagen = "superstar.png",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "AF1",
                        descripcion = "La base de muchos outfits. Versátil y cómoda.",
                        precio = 109990.0,
                        stock = 11,
                        imagen = "af1.png",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idZapas,
                        nombre_producto = "Megaride O1 Azul",
                        descripcion = "Diseño moderno, comodidad diaria.",
                        precio = 79990.0,
                        stock = 14,
                        imagen = "megaride_o1_azul.png",
                        activo = true
                    ),

                    // Accesorios
                    Producto(
                        id_categoria = idAcc,
                        nombre_producto = "Gorra 9Forty NYY",
                        descripcion = "Accesorio urbano para completar tu fit.",
                        precio = 19990.0,
                        stock = 20,
                        imagen = "gorra_9forty_nyy.jpg",
                        activo = true
                    ),
                    Producto(
                        id_categoria = idAcc,
                        nombre_producto = "Calcetines (pack)",
                        descripcion = "Comodidad para el día a día.",
                        precio = 7990.0,
                        stock = 30,
                        imagen = "calcetines2.webp",
                        activo = true
                    ),

                    // Poleras / Polerón (usamos categoría Poleras)
                    Producto(
                        id_categoria = idPoler,
                        nombre_producto = "Polerón SneakerVibe",
                        descripcion = "Cálido, urbano y combinable.",
                        precio = 34990.0,
                        stock = 18,
                        imagen = "poleron_n.png", // si tu archivo es .PNG mayúscula, renómbralo a .png
                        activo = true
                    )
                )

                seedProductos.forEach { productoDao.insertar(it) }
            }
        }
    }
}

