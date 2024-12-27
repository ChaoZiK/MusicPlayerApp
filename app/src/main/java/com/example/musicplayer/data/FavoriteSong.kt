package com.example.musicplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoriteSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val songId: String = "",
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val genre: String? = null,
    val duration: String = "0:00",
    val size: String = "0 MB",
    val path: String = "",
    val artUri: String = "",
    val year: Int? = null,
    val trackNumber: Int? = null,
    val composer: String? = null,
    val addedTimestamp: Long
)

fun FavoriteSong.toSong(): Song {
    return Song(
        id = this.songId,
        title = this.title,
        artist = this.artist,
        album = this.album,
        duration = this.duration,
        artUri = this.artUri,
        path = this.path,
        size = this.size,
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
        album = this.album,
        duration = this.duration,
        artUri = this.artUri,
        addedTimestamp = timestamp,
        path = this.path,
        size = this.size,
        genre = null,
        year = null,
        trackNumber = null,
        composer = null
    )
}
