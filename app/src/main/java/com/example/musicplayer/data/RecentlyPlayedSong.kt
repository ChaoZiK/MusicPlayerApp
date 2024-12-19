package com.example.musicplayer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_played_table")
data class RecentlyPlayedSong(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val songId: String,
    val title: String,
    val artist: String,
    val albumName: String,
    val duration: String,
    val coverImageUrl: String,
    val lastPlayedTimestamp: Long,
    val path: String
)

fun RecentlyPlayedSong.toSong(): Song {
    return Song(
        id = this.songId,
        title = this.title,
        artist = this.artist,
        album = this.albumName,
        duration = this.duration,
        artUri = this.coverImageUrl,
        path = this.path,
        size = "",
        genre = null,
        year = null,
        trackNumber = null,
        composer = null
    )
}

fun Song.toRecentlyPlayedSong(timestamp: Long): RecentlyPlayedSong {
    return RecentlyPlayedSong(
        songId = this.id,
        title = this.title,
        artist = this.artist,
        albumName = this.album,
        duration = this.duration,
        coverImageUrl = this.artUri,
        lastPlayedTimestamp = timestamp,
        path = this.path
    )
}
