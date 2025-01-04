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
    val type: PlaylistType = when (id) {
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