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
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )


        val selection = "${MediaStore.Audio.Media.DATA} LIKE ?"
        val selectionArgs = arrayOf("%/Download/%")

        try {
            val cursor = contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                null
            )

            cursor?.use {
                while (it.moveToNext()) {
                    val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)).toString()
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)) ?: "Unknown Title"
                    val album = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)) ?: "Unknown Album"
                    val artist = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)) ?: "Unknown Artist"
                    val duration = it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                    val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)) ?: ""
                    val artUri = "content://media/external/audio/albumart/$id"

                    if (path.isNotEmpty() && title.isNotEmpty()) {
                        songList.add(
                            Song(
                                id = id,
                                title = title,
                                album = album,
                                artist = artist,
                                duration = formatDuration(duration), // Format duration to string "min:sec"
                                path = path,
                                artUri = artUri
                            )
                        )
                    } else {
                        Log.w(
                            "AudioFetcher",
                            "Skipped song with missing data: ID=$id, Title=$title, Path=$path, Duration=$duration"
                        )

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

    private fun formatDuration(durationInMillis: Long): String {
        val minutes = (durationInMillis / 1000) / 60
        val seconds = (durationInMillis / 1000) % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
