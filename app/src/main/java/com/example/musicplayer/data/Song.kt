package com.example.musicplayer.data

import android.media.MediaMetadataRetriever

data class Song(
    val id: String = "",
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
    val composer: String? = null
)

//Sample for testing!!
val sampleSongs = listOf(
    Song(
        id = "1",
        title = "Shape of You",
        artist = "Ed Sheeran",
        album = "21",
        genre = "Pop",
        duration = "3:53",
        size = "8.5 MB",
        path = "/storage/emulated/0/Music/shape_of_you.mp3",
        year = 2017,
        trackNumber = 1,
        composer = "Ed Sheeran"
    ),
    Song(
        id = "2",
        title = "Someone Like You",
        artist = "Adele",
        album = "21",
        genre = "Pop",
        duration = "4:45",
        size = "9.2 MB",
        path = "/storage/emulated/0/Music/someone_like_you.mp3",
        artUri = "content://media/external/audio/albumart/2",
        year = 2011
    ),
    Song(
        id = "3",
        title = "Bohemian Rhapsody",
        artist = "Queen",
        album = "A Night at the Opera",
        genre = "Rock",
        duration = "5:55",
        size = "12.4 MB",
        path = "/storage/emulated/0/Music/bohemian_rhapsody.mp3",
        year = 1975
    ),
    Song(
        id = "4",
        title = "Blinding Lights",
        artist = "The Weeknd",
        album = "After Hours",
        genre = "Synth-pop",
        duration = "3:20",
        size = "7.8 MB",
        path = "/storage/emulated/0/Music/blinding_lights.mp3",
        year = 2020
    ),
    Song(
        id = "5",
        title = "Hello",
        artist = "Adele",
        album = "25",
        genre = "Pop",
        duration = "4:55",
        size = "10.1 MB",
        path = "/storage/emulated/0/Music/hello.mp3",
        year = 2015
    ),
    Song(
        id = "6",
        title = "Believer",
        artist = "Imagine Dragons",
        album = "Evolve",
        genre = "Pop Rock",
        duration = "3:24",
        size = "8.2 MB",
        path = "/storage/emulated/0/Music/believer.mp3",
        year = 2017
    ),
    Song(
        id = "7",
        title = "Sweet Child O' Mine",
        artist = "Guns N' Roses",
        album = "Appetite for Destruction",
        genre = "Rock",
        duration = "5:56",
        size = "11.5 MB",
        path = "/storage/emulated/0/Music/sweet_child.mp3",
        year = 1987
    ),
    Song(
        id = "8",
        title = "Shake It Off",
        artist = "Taylor Swift",
        album = "1989",
        genre = "Pop",
        duration = "3:39",
        size = "8.7 MB",
        path = "/storage/emulated/0/Music/shake_it_off.mp3",
        year = 2014
    ),
    Song(
        id = "9",
        title = "Uptown Funk",
        artist = "Mark Ronson ft. Bruno Mars",
        album = "Uptown Special",
        genre = "Funk",
        duration = "4:30",
        size = "9.8 MB",
        path = "/storage/emulated/0/Music/uptown_funk.mp3",
        year = 2014
    )
)
fun getImgArt(path: String): ByteArray?{
  val retriever = MediaMetadataRetriever()
  retriever.setDataSource(path)
  return retriever.embeddedPicture
}
fun Song.toFavoritesSong(addedTimestamp: Long): FavoriteSong {
  return FavoriteSong(
    songId = this.id,
    title = this.title,
    artist = this.artist,
    albumName = this.album,
    duration = this.duration,
    coverImageUrl = this.artUri,
    addedTimestamp = addedTimestamp
  )
}
fun FavoriteSong.toSong(path: String): Song {
  return Song(
    id = this.songId,
    title = this.title,
    album = this.albumName,
    artist = this.artist,
    duration = this.duration,
    path = path,
    artUri = this.coverImageUrl
  )
}
