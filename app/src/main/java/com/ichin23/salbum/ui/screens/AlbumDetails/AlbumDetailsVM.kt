package com.ichin23.salbum.ui.screens.AlbumDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.data.api.dto.salbum.rating.SendRatingDTO
import com.ichin23.salbum.domain.models.AlbumDetails
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsVM @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val ratingsRepository: RatingsRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val albumIdArg: String? = savedStateHandle.get<String>("albumId")

    private val _loadingSendRating = MutableStateFlow<Boolean>(false)
    val loadingSendRating: StateFlow<Boolean> = _loadingSendRating.asStateFlow()

    private val _albumDetails = MutableStateFlow<AlbumDetails?>(null)
    val albumDetails: StateFlow<AlbumDetails?> = _albumDetails.asStateFlow()

    fun onEvent(event: AlbumDetailsEvent){
        when(event){
            is AlbumDetailsEvent.SendReviewAlbum -> {
                viewModelScope.launch {
                    _loadingSendRating.value = true
                    albumIdArg?.let {
                        if (albumDetails.value?.userRating != null){
                            ratingsRepository.updateRating(SendRatingDTO(
                                album_id = it,
                                rate = event.rate
                            ))
                        }else{
                            ratingsRepository.addRating(SendRatingDTO(
                                album_id = it,
                                rate = event.rate
                            ))
                        }
                    }
                    _loadingSendRating.value=false
                }
            }
            is AlbumDetailsEvent.AddToListenList -> {
                viewModelScope.launch {
                    albumIdArg?.let { albumRepository.addToListenList(it) }
                    findAlbum()
                }
            }
            is AlbumDetailsEvent.RemoveFromListenList -> {
                viewModelScope.launch {
                    albumIdArg?.let { albumRepository.removeFromListenList(it) }
                    findAlbum()
                }
            }
        }
    }

    fun findAlbum(){
       albumIdArg?.let{albumIdArg ->
            viewModelScope.launch {
                try {
                    _albumDetails.value = albumRepository.getAlbumDetails(albumIdArg)
                }catch (e: HttpException){
                    Log.e("Req error", e.response()?.raw()?.request?.headers.toString())
                    Log.e("Req error", e.response()?.raw()?.request?.url.toString())
                    Log.e("Req error", e.code().toString())
                    Log.e("Req error", e.response()?.body().toString())
                }
            }
        } ?: run {
            Log.w("AlbumDetailsVM", "AlbumID is null")
       }
    }
}