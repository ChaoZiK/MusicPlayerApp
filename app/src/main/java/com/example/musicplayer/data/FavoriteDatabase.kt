package com.example.musicplayer.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*@Database(entities = [FavoriteSong::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
  abstract val favoriteDAO: FavoriteDAO

  companion object{
    @Volatile
    private var INSTANCE: FavoriteDatabase ?= null

    fun getInstance(context: Context): FavoriteDatabase{
      synchronized(this){
        var instance = INSTANCE
        if (instance == null){
          instance = Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            "favorite_db"
          ).build()
        }
        INSTANCE = instance
        return instance
      }
    }
  }
}*/
@Database(entities = [FavoriteSong::class], version = 3, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
  abstract fun favoritesDAO(): FavoriteDAO

  companion object {
    @Volatile
    private var INSTANCE: FavoriteDatabase? = null

    fun getDatabase(context: Context): FavoriteDatabase {
      Log.d("FavoriteDatabase", "Initializing FavoriteDatabase...")
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          FavoriteDatabase::class.java,
          "favorite_database"
        ).fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
