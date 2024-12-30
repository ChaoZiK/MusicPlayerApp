package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.FavoriteSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val favoriteDAO: FavoriteDAO
) : ViewModel() {
    val favoriteSongs: LiveData<List<FavoriteSong>> = favoriteDAO.getAllFavorites()

    fun updateFavoriteSongs(updatedSongs: List<FavoriteSong>) {
        viewModelScope.launch {
            favoriteDAO.deleteAllFavorites()
            updatedSongs.forEach { favoriteDAO.insertFavorite(it) }
        }
    }
}
