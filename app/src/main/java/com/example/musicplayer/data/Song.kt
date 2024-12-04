package com.example.musicplayer.data

data class Song(
    val id: String,
    val title: String,
    val artist: String
)

val sampleSongs = listOf(
    Song(id = "1", title = "Song 1", artist = "Artist 1"),
    Song(id = "2", title = "Song 2", artist = "Artist 2"),
    Song(id = "3", title = "Song 3", artist = "Artist 3"),
    Song(id = "4", title = "Song 4", artist = "Artist 1"),
    Song(id = "5", title = "Song 5", artist = "Artist 2"),
    Song(id = "6", title = "Song 6", artist = "Artist 3"),
    Song(id = "7", title = "Song 7", artist = "Artist 1"),
    Song(id = "8", title = "Song 8", artist = "Artist 2"),
    Song(id = "9", title = "Song 9", artist = "Artist 3"),
)