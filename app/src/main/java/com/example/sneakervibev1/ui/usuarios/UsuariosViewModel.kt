package com.example.sneakervibev1.ui.usuarios
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakervibev1.data.remote.dto.UsuarioDto
import com.example.sneakervibev1.data.repository.UsuarioRepository

import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    private val _usuarios = MutableLiveData<List<UsuarioDto>>()
    val usuarios: LiveData<List<UsuarioDto>> = _usuarios

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                val lista = repository.obtenerUsuario()
                _usuarios.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            }
        }
    }
}