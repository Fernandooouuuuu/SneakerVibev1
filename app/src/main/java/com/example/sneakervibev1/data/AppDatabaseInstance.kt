 //package com.example.sneakervibev1.data

    //import android . content . Context
          //  import androidx . room . Room
         //   import androidx . room . RoomDatabase
         //   import androidx . sqlite . db . SupportSQLiteDatabase
         //   import kotlinx . coroutines . CoroutineScope
         //   import kotlinx . coroutines . Dispatchers
        //    import kotlinx . coroutines . launch

        //    object AppDatabaseInstance {
       //         @Volatile
       //         private var INSTANCE: SneakerVibeDB? = null

      //          // Bandera para asegurar que el seed se ejecute una sola vez por proceso
      //          @Volatile
     //           private var SEEDED: Boolean = false

          //      fun getDatabase(context: Context): SneakerVibeDB =
          //          INSTANCE ?: synchronized(this) {
          //              INSTANCE ?: Room.databaseBuilder(
         //                   context.applicationContext,
         //                   SneakerVibeDB::class.java,
         //                   "sneakervibe.db"
         //               )
         //                   // Solo seed en onCreate (una vez en la vida de la DB)
         //                   .addCallback(object : RoomDatabase.Callback() {
         //                       override fun onCreate(db: SupportSQLiteDatabase) {
        //                            super.onCreate(db)
         //                           // Ejecutar seed en hilo IO
         //                           CoroutineScope(Dispatchers.IO).launch {
        //                               // Doble seguro: evita carrera si onCreate se llama dos veces por algún motivo
        //                                if (!SEEDED) {
       //                                     SEEDED = true
      //                                      INSTANCE?.let { DatabaseSeeder.seed(it) }
      //                                  }
     //                               }
     //                           }
     //                       })
     //                       // OPCIONAL: durante desarrollo, si cambias la schema sin migraciones, borra y recrea
    //                        // .fallbackToDestructiveMigration()
    //                        .build()
   //                         .also { built ->
   //                             INSTANCE = built
   //                             // 👇 IMPORTANTE: ya NO llamamos DatabaseSeeder.seed() aquí
  //                              // para evitar que se ejecute en cada apertura.
  //                          }
  //                  }
//            }
