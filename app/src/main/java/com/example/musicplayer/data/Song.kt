package com.example.musicplayer.data

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String = "",
    val genre: String? = null,
    val duration: String = "0:00",
    val size: String = "0 MB",
    val path: String = ""
)

val sampleSongs = listOf(
    Song(
        id = "1",
        title = "Song 1",
        artist = "Artist 1",
        album = "Album 1",
        genre = "Pop",
        duration = "3:45",
        size = "4.2 MB",
        path = "/storage/emulated/0/Music/song1.mp3"
    ),
    Song(id = "2", title = "Song 2", artist = "Artist 2"),
    Song(id = "3", title = "Song 3", artist = "Artist 3"),
    Song(id = "4", title = "Song 4", artist = "Artist 1"),
    Song(id = "5", title = "Song 5", artist = "Artist 2"),
    Song(id = "6", title = "Song 6", artist = "Artist 3"),
    Song(id = "7", title = "Song 7", artist = "Artist 1"),
    Song(id = "8", title = "Song 8", artist = "Artist 2"),
    Song(id = "9", title = "Song 9", artist = "Artist 3")
)