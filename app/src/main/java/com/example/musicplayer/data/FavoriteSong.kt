package com.example.musicplayer.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val songId: String,
    val title: String,
    val artist: String,
    val albumName: String,
    val duration: String,
    val coverImageUrl: String,
    val addedTimestamp: Long
)
