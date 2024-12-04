package com.example.musicplayer.data

data class Playlist(
    val id: String,
    val title: String,
    val songs: List<Song> = emptyList(),
    val isDefault: Boolean = false
) {
    val songCount: Int get() = songs.size
}



val defaultPlaylists = listOf(
    Playlist(
        id = "favorite",
        title = "My favourite",
        songs = sampleSongs,
        isDefault = true
    ),
    Playlist(
        id = "recent",
        title = "Recently played",
        songs = sampleSongs,
        isDefault = true
    ),
)

val customPlaylists = listOf(
    Playlist(id = "playlist1", title = "d"),
    Playlist(id = "playlist2", title = "indie", songs = listOf()),
    Playlist(id = "playlist3", title = "sportvn"),
    Playlist(id = "playlist4", title = "d"),
    Playlist(id = "playlist5", title = "indie", songs = listOf()),
    Playlist(id = "playlist6", title = "sportvn"),
    Playlist(id = "playlist7", title = "d"),
    Playlist(id = "playlist8", title = "indie", songs = listOf()),
    Playlist(id = "playlist9", title = "sportvn")
)