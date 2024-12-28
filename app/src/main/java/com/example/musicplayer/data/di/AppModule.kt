package com.example.musicplayer.data.di

import android.app.Application
import android.content.Context
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideMusicController(
        context: Context,
        playerRepository: dagger.Lazy<PlayerRepository>
    ): MusicController {
        return MusicController(context) { song ->
            playerRepository.get().updateSong(song)
        }
    }

    @Provides
    @Singleton
    fun providePlayerRepository(
        musicController: MusicController,
        favoriteDAO: FavoriteDAO
    ): PlayerRepository {
        return PlayerRepository(musicController, favoriteDAO)
    }
}

