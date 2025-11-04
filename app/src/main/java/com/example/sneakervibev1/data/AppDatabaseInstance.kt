package com.example.sneakervibev1.data

import android.content.Context
import androidx.room.Room

object AppDatabaseInstance {
    @Volatile
    private var INSTANCE: SneakerVibeBD? = null

    fun getDatabase(context: Context): SneakerVibeBD {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                SneakerVibeBD::class.java,
                "sneakervibe.db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}