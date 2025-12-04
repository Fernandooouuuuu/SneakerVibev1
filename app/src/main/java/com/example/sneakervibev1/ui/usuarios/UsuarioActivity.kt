package com.example.sneakervibev1.ui.usuarios

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sneakervibev1.R

class UsuariosActivity : AppCompatActivity() {

    private val viewModel: UsuariosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        viewModel.usuarios.observe(this, Observer { lista ->
            txtResultado.text = "Usuarios: \n" + lista.joinToString("\n") {
                "- ${it.id}: ${it.nombre} ${it.apellido} (${it.email})"
            }
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            if (errorMsg != null) {
                txtResultado.text = "Error: $errorMsg"
            }
        })

        // dispara la llamada a la API
        viewModel.cargarUsuarios()
    }
}