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
