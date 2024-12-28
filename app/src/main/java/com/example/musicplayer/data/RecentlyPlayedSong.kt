package com.example.musicplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_played_table")
data class RecentlyPlayedSong(
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
    val lastPlayedTimestamp: Long,
)

fun RecentlyPlayedSong.toSong(): Song {
    return Song(
        id = this.songId,
        title = this.title,
        artist = this.artist,
        album = this.album,
        duration = this.duration,
        artUri = this.artUri,
        path = this.path,
        size = this.size,
        genre = this.genre,
        year = this.year,
        trackNumber = this.trackNumber,
        composer = this.composer
    )
}

fun Song.toRecentlyPlayedSong(timestamp: Long): RecentlyPlayedSong {
    return RecentlyPlayedSong(
        songId = this.id,
        title = this.title,
        artist = this.artist,
        album = this.album,
        duration = this.duration,
        artUri = this.artUri,
        lastPlayedTimestamp = timestamp,
        path = this.path,
        size = this.size,
        genre = this.genre,
        year = this.year,
        trackNumber = this.trackNumber,
        composer = this.composer
    )
}
