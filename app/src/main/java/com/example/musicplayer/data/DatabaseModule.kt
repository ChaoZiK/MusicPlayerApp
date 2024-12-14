package com.example.musicplayer.data

import android.content.Context
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.FavoriteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return FavoriteDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteDAO(favoriteDatabase: FavoriteDatabase): FavoriteDAO {
        return favoriteDatabase.favoritesDAO()
    }
}