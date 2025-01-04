package com.example.musicplayer

import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.repository.PlayerRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class PlayerRepositoryTest {

    private lateinit var playerRepository: PlayerRepository
    private lateinit var mockMusicController: MusicController
    private lateinit var mockFavoriteDAO: FavoriteDAO

    @Before
    fun setUp() {
        mockMusicController = mock(MusicController::class.java)
        mockFavoriteDAO = mock(FavoriteDAO::class.java)

        playerRepository = PlayerRepository(mockMusicController, mockFavoriteDAO)
    }

    @Test
    fun `test updatePlaylist updates playlist`() = runTest {
        val songs = listOf(
            Song("1", "Song 1", "Artist 1", "Album 1", "3:00", "/path/song1.mp3", ""),
            Song("2", "Song 2", "Artist 2", "Album 2", "4:00", "/path/song2.mp3", "")
        )

        playerRepository.updatePlaylist(songs)

        val playlist = playerRepository.playlist.first()
        assert(playlist == songs)
    }

    @Test
    fun `test togglePlayPause toggles playback state`() {
        playerRepository.togglePlayPause()
        verify(mockMusicController).continuePlaying()

        playerRepository.togglePlayPause()
        verify(mockMusicController, times(1)).togglePlayPause()
    }
}
