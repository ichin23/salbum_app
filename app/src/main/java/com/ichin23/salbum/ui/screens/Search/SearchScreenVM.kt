package com.ichin23.salbum.ui.screens.Search

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var _searchResult = MutableStateFlow<List<Album>>(arrayListOf())
    var searchResult = _searchResult.asStateFlow()

    var _loading = MutableStateFlow<Boolean>(false)
    var loading = _loading.asStateFlow()

    fun search(query:String){
        viewModelScope.launch {
            _loading.value = true
            delay(1000)
            _searchResult.value = albumRepository.searchAlbum(query)
            _loading.value = false
        }
    }
}