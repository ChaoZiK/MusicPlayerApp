package com.example.musicplayer.backend


import android.content.Context
import com.example.musicplayer.backend.AudioFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAudioFetcher(
        @ApplicationContext context: Context
    ): AudioFetcher {
        return AudioFetcher(context)
    }
}