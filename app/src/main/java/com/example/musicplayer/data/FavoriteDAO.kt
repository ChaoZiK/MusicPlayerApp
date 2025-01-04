package com.example.musicplayer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: FavoriteSong): Long

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteSong)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()

    @Query("SELECT * FROM favorites_table ORDER BY addedTimestamp DESC")
    fun getAllFavorites(): LiveData<List<FavoriteSong>>

    @Query("SELECT * FROM favorites_table WHERE songId = :songId LIMIT 1")
    suspend fun getFavoriteBySongId(songId: String): FavoriteSong?
}
