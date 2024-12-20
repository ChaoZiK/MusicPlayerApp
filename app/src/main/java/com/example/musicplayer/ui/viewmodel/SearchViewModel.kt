package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun activateSearch() {
        _isSearchActive.value = true
    }

    fun deactivateSearch() {
        _isSearchActive.value = false
    }

    fun onSearchTextChange(newText: String) {
        _searchText.update { newText }
    }
}