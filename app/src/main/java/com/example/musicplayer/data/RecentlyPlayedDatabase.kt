package com.example.musicplayer.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentlyPlayedSong::class], version = 2, exportSchema = false)
abstract class RecentlyPlayedDatabase : RoomDatabase() {
    abstract fun recentlyPlayedDAO(): RecentlyPlayedDAO

    companion object {
        @Volatile
        private var INSTANCE: RecentlyPlayedDatabase? = null

        fun getDatabase(context: Context): RecentlyPlayedDatabase {
            Log.d("RecentlyPlayedDatabase", "Initializing RecentlyPlayedDatabase...")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecentlyPlayedDatabase::class.java,
                    "recently_played_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}