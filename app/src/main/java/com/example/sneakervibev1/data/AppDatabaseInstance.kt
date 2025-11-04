package com.example.sneakervibev1.data

import android.content.Context
import androidx.room.Room

object AppDatabaseInstance {
    @Volatile
    private var INSTANCE: SneakerVibeDB? = null

    fun getDatabase(context: Context): SneakerVibeDB =
        INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                SneakerVibeDB::class.java,
                "sneakervibe.db"
            )
                .addCallback(object : androidx.room.RoomDatabase.Callback() {
                    override fun onCreate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Cuando la base se crea por primera vez, ejecutamos el seeder
                        INSTANCE?.let { DatabaseSeeder.seed(it) }
                    }
                })
                .build()
                .also {
                    INSTANCE = it
                    // Si ya existía (no es la primera vez), también aseguramos seed
                    DatabaseSeeder.seed(it)
                }
        }
}
