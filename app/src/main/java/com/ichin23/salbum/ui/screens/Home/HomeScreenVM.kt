package com.ichin23.salbum.ui.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.rating.LatestOneDTO
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenVM @Inject constructor(
    val albumRepository: AlbumRepository,
    val salbumService: ApiSalbumService,
    val ratingsRepository: RatingsRepository
): ViewModel() {

    var _albumsList = MutableStateFlow<List<Album>>(arrayListOf())
    var albums = _albumsList.asStateFlow();

    val _ratingsList = MutableStateFlow<LatestOneDTO?>(null)
    var ratings = _ratingsList.asStateFlow()

    val _refreshing = MutableStateFlow<Boolean>(false)
    val refreshing = _refreshing.asStateFlow()
    //var albums:List<Album> by mutableStateOf<List<Album>>(arrayListOf())
    //var ratings: List<Ratings> by mutableStateOf(arrayListOf())

    init{
        refresh()
    }

    fun refresh(){
        viewModelScope.launch {
            _refreshing.value = true
            _albumsList.value=albumRepository.getAllAlbums()
            _ratingsList.value=salbumService.getLatestOne()
            _refreshing.value=false
        }
    }
}