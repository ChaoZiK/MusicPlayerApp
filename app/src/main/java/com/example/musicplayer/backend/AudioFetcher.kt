package com.example.musicplayer.backend

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.musicplayer.data.Song

class AudioFetcher(private val context: Context) {

    fun fetchAudioFiles(): List<Song> {
        val songList = mutableListOf<Song>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )

        // Selection to filter out system sounds and include only music files
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        try {
            val cursor = contentResolver.query(uri, projection, selection, null, null)

            cursor?.use {
                while (it.moveToNext()) {
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)) ?: "Unknown Title"
                    val album = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)) ?: "Unknown Album"
                    val artist = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) ?: "Unknown Artist"
                    val duration = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)) ?: "0"
                    val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)) ?: ""

                    if (path.isNotEmpty()) {
                        songList.add(
                            Song(
                                title = title,
                                album = album,
                                artist = artist,
                                duration = duration,
                                path = path
                            )
                        )
                    } else {
                        Log.w("AudioFetcher", "Skipped a song with an empty path.")
                    }
                }
            } ?: run {
                Log.e("AudioFetcher", "Cursor is null. Query might have failed.")
            }
        } catch (e: Exception) {
            Log.e("AudioFetcher", "Error fetching audio files: ${e.message}", e)
        }
        return songList
    }
}