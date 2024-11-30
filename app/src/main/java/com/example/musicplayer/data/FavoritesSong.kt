package com.example.musicplayer.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoritesSong(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val songId: String,
  val title: String,
  val artist: String,
  val albumName: String,
  val duration: Long,
  val coverImageUrl: String,
  val addedTimestamp: Long
)
