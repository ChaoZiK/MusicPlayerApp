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
    val addedTimestamp: Long,
    val path: String
)

fun FavoriteSong.toSong(): Song {
    return Song(
        id = this.songId,
        title = this.title,
        artist = this.artist,
        album = this.albumName,
        duration = this.duration,
        artUri = this.coverImageUrl,
        path = "",
        size = "",
        genre = null,
        year = null,
        trackNumber = null,
        composer = null
    )
}

fun Song.toFavoriteSong(timestamp: Long): FavoriteSong {
    return FavoriteSong(
        songId = this.id,
        title = this.title,
        artist = this.artist,
        albumName = "", // Update if album data exists
        duration = this.duration,
        coverImageUrl = this.artUri,
        addedTimestamp = timestamp,
        path = this.path
    )
}

