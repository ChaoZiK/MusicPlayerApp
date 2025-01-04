package com.example.musicplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.repository.PlayerRepository
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class FullPlayerViewModelTesting {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FullPlayerViewModel
    private lateinit var playerRepository: PlayerRepository
    private lateinit var musicController: MusicController
    private lateinit var favoriteDAO: FavoriteDAO

    @Before
    fun setUp() {
        musicController = mock()
        favoriteDAO = mock()

        playerRepository = PlayerRepository(musicController, favoriteDAO)

        playerRepository.isPlaying = MutableStateFlow(false)
        playerRepository.currentSong = MutableStateFlow(null)

        viewModel = FullPlayerViewModel(playerRepository, musicController, favoriteDAO, mock())
    }

    @Test
    fun `test togglePlayPause`() = runTest {
        viewModel.togglePlayPause()
        assertEquals(true, playerRepository.isPlaying.value)

        viewModel.togglePlayPause()
        assertEquals(false, playerRepository.isPlaying.value)
    }

    @Test
    fun `test updateSystemVolume`() = runTest {
        viewModel.updateSystemVolume(0.8f)
        assertEquals(0.8f, playerRepository.volume.value)
    }

    @Test
    fun `test playNext updates current song`() = runTest {
        val song1 = Song("1", "Song 1", "Artist 1", "Album 1", "3:00", "path1", "uri1")
        val song2 = Song("2", "Song 2", "Artist 2", "Album 2", "4:00", "path2", "uri2")

        playerRepository.updatePlaylist(listOf(song1, song2))
        playerRepository.updateSongByIndex(0)

        viewModel.playNext()

        assertEquals(song2, playerRepository.currentSong.value)
    }

    @Test
    fun `test toggleFavorite`() = runTest {
        val song = Song("1", "Song 1", "Artist 1", "Album 1", "3:00", "path1", "uri1")

        playerRepository.updatePlaylist(listOf(song))
        playerRepository.updateSongByIndex(0)

        viewModel.toggleFavorite()

        assertEquals(true, playerRepository.isFavorite.value)
    }
}
