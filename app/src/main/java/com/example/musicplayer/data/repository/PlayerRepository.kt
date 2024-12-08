package com.example.musicplayer.data.repository

import android.net.Uri
import com.example.musicplayer.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
) {
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private val _currentTime = MutableStateFlow("00:00")
    val currentTime = _currentTime.asStateFlow()

    private val _totalTime = MutableStateFlow("00:00")
    val totalTime = _totalTime.asStateFlow()

    private val _playlist = MutableStateFlow<List<Song>>(emptyList())
    private val _currentIndex = MutableStateFlow(0)
    private val _artUri = MutableStateFlow<String?>(null)
    val artUri = _artUri.asStateFlow()

    private val _songTitle = MutableStateFlow("")
    val songTitle = _songTitle.asStateFlow()

    private val _artist = MutableStateFlow("")
    val artist = _artist.asStateFlow()

    private var progressUpdateJob: Job? = null

    private val _volume = MutableStateFlow(0.5f)
    val volume = _volume.asStateFlow()

    fun updateVolume(newVolume: Float) {
        _volume.value = newVolume.coerceIn(0f, 1f)
    }

    fun updateSong(song: Song) {
        _currentSong.value = song
        _totalTime.value = song.duration
        _songTitle.value = song.title
        _artist.value = song.artist
        _artUri.value = song.artUri
        resetProgress()
    }


    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value

        if (_isPlaying.value) {
            progressUpdateJob?.cancel()
            progressUpdateJob = scope.launch {
                startProgressUpdate()
            }
        } else {
            progressUpdateJob?.cancel()
        }
    }

    fun stopPlayback() {
        progressUpdateJob?.cancel()
        _isPlaying.value = false
    }

    private fun resetProgress() {
        _progress.value = 0f
        _currentTime.value = "00:00"
    }

    fun updateProgress(newProgress: Float) {
        _progress.value = newProgress.coerceIn(0f, 1f)
        _currentTime.value = calculateTime(newProgress)
    }

    private fun calculateTime(progress: Float): String {
        val totalSeconds = (_totalTime.value.split(":")[0].toInt() * 60) +
                _totalTime.value.split(":")[1].toInt()
        val currentSeconds = (totalSeconds * progress).toInt()
        val minutes = currentSeconds / 60
        val seconds = currentSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    private suspend fun startProgressUpdate() {
        val updatesPerSecond = 60
        val delayTime = 1000L / updatesPerSecond

        while (true) {
            delay(delayTime)
            if (_isPlaying.value) {
                val totalSeconds = (_totalTime.value.split(":")[0].toInt() * 60) +
                        _totalTime.value.split(":")[1].toInt()
                val deltaProgress = 1f / (totalSeconds * updatesPerSecond)
                val currentProgress = _progress.value

                val nextProgress = currentProgress + deltaProgress
                if (nextProgress >= 1f) {
                    resetProgress()
                    _isPlaying.value = false
                } else {
                    updateProgress(nextProgress)
                }
            }
        }
    }

}