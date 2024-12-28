package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.FavoriteSong
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val favoriteDAO: FavoriteDAO
) : ViewModel() {
    // Use LiveData from DAO directly
    val favoriteSongs: LiveData<List<FavoriteSong>> = favoriteDAO.getAllFavorites()
}
