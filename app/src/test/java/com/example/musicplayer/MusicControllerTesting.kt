package com.example.musicplayer

import android.content.Context
import android.media.AudioManager
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.Song
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MusicControllerTesting {

    private lateinit var musicController: MusicController
    private lateinit var mockContext: Context
    private lateinit var mockAudioManager: AudioManager

    @Before
    fun setUp() {
        mockContext = mock()
        mockAudioManager = mock()
        whenever(mockContext.getSystemService(Context.AUDIO_SERVICE)).thenReturn(mockAudioManager)

        musicController = MusicController(mockContext) { }
    }

    @Test
    fun `test setVolume adjusts system volume`() {
        val maxVolume = 10
        val volume = 0.5f
        whenever(mockAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)).thenReturn(maxVolume)

        musicController.setVolume(volume)

        verify(mockAudioManager).setStreamVolume(
            eq(AudioManager.STREAM_MUSIC),
            eq((volume * maxVolume).toInt()),
            eq(0)
        )
    }

    @Test
    fun `test playSong starts playback`() {
        val song = Song(
            id = "1",
            title = "Test Song",
            artist = "Test Artist",
            album = "Test Album",
            duration = "3:45",
            path = "/path/to/song",
            artUri = "content://media/external/audio/albumart/1"
        )

        musicController.playSong(song)

        assertTrue(musicController.isPlaying())
    }

    @Test
    fun `test stop stops playback`() {
        musicController.stop()
        assertFalse(musicController.isPlaying())
    }
}
