package com.example.musicplayer.data

enum class PlaylistType {
    FAVOURITE,
    RECENT,
    CUSTOM
}

data class Playlist(
    val id: String,
    val title: String,
    val songs: List<Song> = emptyList(),
    val isDefault: Boolean = false,
    val coverImage: String? = null
) {
    val type: PlaylistType = when(id) {
        "favorite" -> PlaylistType.FAVOURITE
        "recent" -> PlaylistType.RECENT
        else -> PlaylistType.CUSTOM
    }
    val songCount: Int get() = songs.size
}

val defaultPlaylists = listOf(
    Playlist(
        id = "recent",
        title = "Recently played songs",
        songs = listOf(),
        isDefault = true
    ),
    Playlist(
        id = "favorite",
        title = "My favorite songs",
        songs = listOf(),
        isDefault = true
    )
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