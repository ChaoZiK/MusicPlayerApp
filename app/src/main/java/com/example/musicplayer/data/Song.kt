package com.example.musicplayer.data

import android.media.MediaMetadataRetriever

data class Song(
  val id: String ="",
  val title: String,
  val album: String,
  val artist: String,
  val duration: String = "",
  val path: String,
  val artUri: String =""
)
/*fun formatDuration(duration: Long): String{
  val minutes = java.util.concurrent.TimeUnit.MINUTES.convert(duration, java.util.concurrent.TimeUnit.MILLISECONDS)
  val seconds = (java.util.concurrent.TimeUnit.SECONDS.convert(duration, java.util.concurrent.TimeUnit.MILLISECONDS) -
    minutes * java.util.concurrent.TimeUnit.SECONDS.convert(1, java.util.concurrent.TimeUnit.MINUTES))
  return String.format("%02d:%02d", minutes, seconds)
}*/
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


//fun setSongPosition(increment: Boolean) {
//  if (!PlayerActivity.repeat) {
//   if (increment) {
//      if (PlayerActivity.musicListPA.size - 1 == PlayerActivity.songPosition)
//        PlayerActivity.songPosition = 0
//      else ++PlayerActivity.songPosition
//    } else {
//      if (0 == PlayerActivity.songPosition)
//        PlayerActivity.songPosition = PlayerActivity.musicListPA.size - 1
//      else --PlayerActivity.songPosition
//    }
//  }
//}
//fun exitApplication() {
//  if (PlayerActivity.musicService != null) {
//    PlayerActivity.musicService!!.audioManager.abandonAudioFocus(PlayerActivity.musicService)
//    PlayerActivity.musicService!!.stopForeground(true)
//    PlayerActivity.musicService!!.mediaPlayer!!.release()
//    PlayerActivity.musicService = null
//  }
//  exitProcess(1)
//}
//fun setDialogBtnBackground(context: Context, dialog: AlertDialog) {
//setting button text
//  dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setTextColor(
//    MaterialColors.getColor(context, R.attr.dialogTextColor, Color.WHITE)
//  )
//  dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
//    MaterialColors.getColor(context, R.attr.dialogTextColor, Color.WHITE)
//  )

//setting button background
//  dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)?.setBackgroundColor(
//    MaterialColors.getColor(context, R.attr.dialogBtnBackground, Color.RED)
//  )
//  dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)?.setBackgroundColor(
//    MaterialColors.getColor(context, R.attr.dialogBtnBackground, Color.RED)
//  )
//}
//fun getMainColor(img: Bitmap): Int {
//  val newImg = Bitmap.createScaledBitmap(img, 1, 1, true)
//  val color = newImg.getPixel(0, 0)
//  newImg.recycle()
//  return color
//}

//fun favouriteChecker(id: String): Int {
//  PlayerActivity.isFavourite = false
//  FavouriteActivity.favouriteSongs.forEachIndexed { index, music ->
//    if (id == music.id) {
//      PlayerActivity.isFavourite = true
//      return index
//    }
//  }
//  return -1
//}
