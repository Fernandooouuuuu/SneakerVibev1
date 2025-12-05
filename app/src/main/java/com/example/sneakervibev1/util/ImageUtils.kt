package com.example.sneakervibev1.util

import android.content.Context
import com.example.sneakervibev1.R

fun obtenerDrawableProducto(ctx: Context, imagen: String?): Int {
    if (imagen.isNullOrBlank()) return R.drawable.placeholder

    // "campus00s_r1.png" → "campus00s_r1"
    val nombreSinExtension = imagen.substringBeforeLast('.')

    val resId = ctx.resources.getIdentifier(
        nombreSinExtension,
        "drawable",
        ctx.packageName
    )

    return if (resId != 0) resId else R.drawable.placeholder
}