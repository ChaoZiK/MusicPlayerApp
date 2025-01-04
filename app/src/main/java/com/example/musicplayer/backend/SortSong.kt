package com.example.musicplayer.backend

import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.data.AlphabeticDirection
import com.example.musicplayer.data.DurationDirection
import com.example.musicplayer.data.TimeDirection

fun sortSongs(
    songs: List<Song>,
    sortOption: SortOption,
    sortDirection: SortDirection
): List<Song> {
    val comparator: Comparator<Song> = when (sortOption) {
        SortOption.SONG_NAME -> compareBy { it.title.lowercase() }
        SortOption.ARTIST_NAME -> compareBy { it.artist.lowercase() }
        SortOption.DURATION -> compareBy {
            it.duration.split(":").let { parts ->
                parts[0].toInt() * 60 + parts[1].toInt()
            }
        }
    }

    return if (
        sortDirection is AlphabeticDirection.ZToA ||
        sortDirection is TimeDirection.OldToNew ||
        sortDirection is DurationDirection.LongToShort
    ) {
        songs.sortedWith(comparator.reversed())
    } else {
        songs.sortedWith(comparator)
    }
}
