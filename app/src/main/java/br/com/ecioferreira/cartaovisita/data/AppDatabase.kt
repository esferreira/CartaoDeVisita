package br.com.ecioferreira.cartaovisita.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartaoVisita::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun cartaoDao(): CartaoVisitaDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?:synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cartaovisita_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}