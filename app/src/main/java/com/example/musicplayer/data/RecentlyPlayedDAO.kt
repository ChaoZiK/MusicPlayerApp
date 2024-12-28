package com.example.musicplayer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecentlyPlayedDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentlyPlayed(song: RecentlyPlayedSong)

    @Query("SELECT * FROM recently_played_table ORDER BY lastPlayedTimestamp DESC")
    fun getAllRecentlyPlayed(): LiveData<List<RecentlyPlayedSong>>

    @Query("DELETE FROM recently_played_table WHERE id NOT IN (SELECT id FROM recently_played_table ORDER BY lastPlayedTimestamp DESC LIMIT 100)")
    suspend fun trimRecentlyPlayed()

    @Query("SELECT * FROM recently_played_table WHERE songId = :songId LIMIT 1")
    suspend fun getRecentlyPlayedBySongId(songId: String): RecentlyPlayedSong?

    @Update
    suspend fun updateRecentlyPlayed(song: RecentlyPlayedSong)

    @Query("DELETE FROM recently_played_table")
    suspend fun deleteAllRecentlyPlayed()
}