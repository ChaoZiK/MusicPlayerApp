package com.example.musicplayer.data.repository

import android.util.Log
import com.example.musicplayer.backend.MusicController
import android.net.Uri
import com.example.musicplayer.data.FavoriteDAO
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
  private val musicController: MusicController,
  private val favoriteDAO: FavoriteDAO
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
  val playlist = _playlist.asStateFlow()

  private val _currentIndex = MutableStateFlow(0)
  private val _artUri = MutableStateFlow<String?>(null)
  val artUri = _artUri.asStateFlow()

  private val _songTitle = MutableStateFlow("")
  val songTitle = _songTitle.asStateFlow()

  private val _artist = MutableStateFlow("")
  val artist = _artist.asStateFlow()

  private val _isFavorite = MutableStateFlow(false)
  val isFavorite = _isFavorite.asStateFlow()

  private val _isShuffleEnabled = MutableStateFlow(false)
  val isShuffleEnabled = _isShuffleEnabled.asStateFlow()

  private val _repeatMode = MutableStateFlow(RepeatMode.NONE)
  val repeatMode = _repeatMode.asStateFlow()

  enum class RepeatMode {
    NONE, ALL, ONE
  }

  private val _volume = MutableStateFlow(0.5f)
  val volume = _volume.asStateFlow()

  private var progressUpdateJob: Job? = null

  fun getIsFavorite(): Boolean {
    return _isFavorite.value
  }

  fun syncVolumeWithSystem() {
    val systemVolume = musicController.getVolume()
    _volume.value = systemVolume
  }

  fun updateSong(song: Song) {
    _currentSong.value = song
    _totalTime.value = song.duration
    _songTitle.value = song.title
    _artist.value = song.artist
    _artUri.value = song.artUri
    resetProgress()
    _isPlaying.value = false

    CoroutineScope(Dispatchers.IO).launch {
      val isFavoriteSong = favoriteDAO.getFavoriteBySongId(song.id) != null
      _isFavorite.value = isFavoriteSong
    }
  }

  fun shuffle() {
    if (_playlist.value.isNotEmpty()) {
      val playlistSize = _playlist.value.size

      if (playlistSize <= 1) {
        return
      }

      val currentIndex = _playlist.value.indexOf(_currentSong.value)
      val randomIndex = (0 until playlistSize)
        .filter { it != currentIndex }
        .random()
      updateSongByIndex(randomIndex)
      musicController.playSong(_playlist.value[randomIndex])
      _isPlaying.value = false
    }
  }

  fun playFirstSong() {
    if (_playlist.value.isNotEmpty()) {
      val firstSong = _playlist.value[0]
      // Check if the first song is already playing
      if (_currentSong.value == firstSong && _isPlaying.value) {
        Log.d("PlayerRepository", "First song is already playing. No action taken.")
        return
      }
      updateSongByIndex(0)
      musicController.playSong(firstSong)
      _isPlaying.value = false
    }
  }

  fun playOrShuffle() {
    if (_isPlaying.value) {
      musicController.continuePlaying()
    } else {
      togglePlayPause()
    }
  }

  fun togglePlayPause() {
    _isPlaying.value = !_isPlaying.value

    if (_isPlaying.value) {
      progressUpdateJob?.cancel()
      progressUpdateJob = scope.launch {
        startProgressUpdate()
      }
      musicController.continuePlaying()
    } else {
      progressUpdateJob?.cancel()
      musicController.togglePlayPause()
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

  fun updatePlaylist(newPlaylist: List<Song>) {
    _playlist.value = newPlaylist
    _currentIndex.value = 0
  }

  fun updateSongByIndex(index: Int) {
    if (index in _playlist.value.indices) {
      _currentIndex.value = index
      updateSong(_playlist.value[index])
    } else {
      Log.e("PlayerRepository", "Invalid song index: $index")
    }
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
          when (_repeatMode.value) {
            RepeatMode.ONE -> {
              resetProgress()
              musicController.stop()
              musicController.playSong(_currentSong.value!!)
              _isPlaying.value = true
            }
            RepeatMode.ALL -> nextSong()
            RepeatMode.NONE -> {
              nextSong() ?: run {
                resetProgress()
                _isPlaying.value = false
              }
            }
          }
        } else {
          updateProgress(nextProgress)
        }
      }
    }
  }

  fun nextSong(): Song? {
    if (_playlist.value.isEmpty()) {
      Log.e("PlayerRepository", "Playlist is empty. Cannot play next song.")
      return null
    }


    val newIndex = when {
      _isShuffleEnabled.value -> {
        if (_playlist.value.size > 1) {
          (0 until _playlist.value.size).filter { it != _currentIndex.value }.random()
        } else {
          _currentIndex.value
        }
      }

      _repeatMode.value == RepeatMode.NONE && _currentIndex.value + 1 >= _playlist.value.size -> {
        return null
      }

      else -> {
        if (_currentIndex.value + 1 >= _playlist.value.size) {
          0
        } else {
          _currentIndex.value + 1
        }
      }
    }

    _currentIndex.value = newIndex
    val nextSong = _playlist.value[newIndex]
    updateSong(nextSong)
    musicController.playSong(nextSong)
    restartProgressUpdates()
    _isPlaying.value = true
    return nextSong
  }

  fun previousSong(): Song? {
    if (_playlist.value.isEmpty()) {
      Log.e("PlayerRepository", "Playlist is empty. Cannot play previous song.")
      return null
    }

    val newIndex = when {
      _repeatMode.value == RepeatMode.NONE && _currentIndex.value == 0 -> {
        _currentIndex.value
      }

      else -> {
        if (_currentIndex.value - 1 < 0) {
          _playlist.value.size - 1
        } else {
          _currentIndex.value - 1
        }
      }
    }

    if (newIndex != _currentIndex.value) {
      _currentIndex.value = newIndex
      val previousSong = _playlist.value[newIndex]
      updateSong(previousSong)
      musicController.playSong(previousSong)
      restartProgressUpdates()
      _isPlaying.value = true
      return previousSong
    } else {
      return _playlist.value[_currentIndex.value]
    }
  }


  private fun restartProgressUpdates() {
    progressUpdateJob?.cancel()
    progressUpdateJob = scope.launch {
      startProgressUpdate()
    }
  }

  fun seekToProgress(newProgress: Float) {
    val totalSeconds = (_totalTime.value.split(":")[0].toInt() * 60) +
            _totalTime.value.split(":")[1].toInt()
    val seekPositionMillis = (totalSeconds * newProgress).toInt() * 1000
    musicController.seekTo(seekPositionMillis)
    _progress.value = newProgress
    _currentTime.value = calculateTime(newProgress)
  }

  fun toggleShuffle() {
    _isShuffleEnabled.value = !_isShuffleEnabled.value
  }

  fun toggleRepeat() {
    _repeatMode.value = when (_repeatMode.value) {
      RepeatMode.NONE -> RepeatMode.ALL
      RepeatMode.ALL -> RepeatMode.ONE
      RepeatMode.ONE -> RepeatMode.NONE
    }
  }

  fun toggleFavorite(song: Song?) {
    if (song != null) {
      _isFavorite.value = !_isFavorite.value
      Log.d("PlayerRepository", "Favorite toggled for song: ${song.title}")
    }
  }

  private fun parseDurationToSeconds(duration: String): Int {
    val parts = duration.split(":")
    return if (parts.size == 2) {
      parts[0].toInt() * 60 + parts[1].toInt()
    } else {
      0
    }
  }
}
