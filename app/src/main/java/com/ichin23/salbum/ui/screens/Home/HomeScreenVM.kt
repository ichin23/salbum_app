package com.ichin23.salbum.ui.screens.Home

import android.media.Rating
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ichin23.salbum.data.albumsJSON
import com.ichin23.salbum.data.ratingsJSON
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.utils.LocalDateTimeDeserializer
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@HiltViewModel
class HomeScreenVM @Inject constructor(val albumRepository: AlbumRepository, val ratingsRepository: RatingsRepository): ViewModel() {

    var _albumsList = MutableStateFlow<List<Album>>(arrayListOf())
    var albums = _albumsList.asStateFlow();

    val _ratingsList = MutableStateFlow<List<Ratings>>(arrayListOf())
    var ratings = _ratingsList.asStateFlow()

    val _refreshing = MutableStateFlow<Boolean>(false)
    val refreshing = _refreshing.asStateFlow()
    //var albums:List<Album> by mutableStateOf<List<Album>>(arrayListOf())
    //var ratings: List<Ratings> by mutableStateOf(arrayListOf())

    init{
        _albumsList.value=albumRepository.getAllAlbums()
        _ratingsList.value=ratingsRepository.getAllRatings()
    }

    fun refresh(){
        viewModelScope.launch {
            _refreshing.value = true
            delay(1000)
            _albumsList.value=albumRepository.getAllAlbums()
            _ratingsList.value=ratingsRepository.getAllRatings()
            _refreshing.value=false
        }
    }
}