package com.example.networkguardian

import android.content.Context
import androidx.room.*

@Database(entities = [NetworkEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun networkEventDao(): NetworkEventDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "network_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
