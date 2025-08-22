package com.ichin23.salbum.ui.screens.AlbumDetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ichin23.salbum.data.api.APIMusicBrainzService
import com.ichin23.salbum.data.api.APIReleasesImagesService
import com.ichin23.salbum.data.api.MusicBrainzApi
import com.ichin23.salbum.data.api.dto.musicbrainz.release.ReleaseDTO
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.models.Ratings
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsVM @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val ratingsRepository: RatingsRepository,
    private val userRepository: UserRepository,
    private val musicBrainzApi: APIMusicBrainzService,
    private val imagesService: APIReleasesImagesService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val albumIdArg: String? = savedStateHandle.get<String>("albumId") ?:
    throw IllegalArgumentException("Album ID is required")

    private val _loadingSendRating = MutableStateFlow<Boolean>(false)
    val loadingSendRating: StateFlow<Boolean> = _loadingSendRating.asStateFlow()

    private val _albumDetails = MutableStateFlow<Album?>(null)
    val albumDetails: StateFlow<Album?> = _albumDetails.asStateFlow()

    private val _albumTest = MutableStateFlow<ReleaseDTO?>(null)
    val albumTest: StateFlow<ReleaseDTO?> = _albumTest.asStateFlow()

    private val _albumReviews = MutableStateFlow<List<Ratings>>(arrayListOf())
    val reviews: StateFlow<List<Ratings>> = _albumReviews.asStateFlow()

    init{
        findAlbum(albumIdArg)
        loadRatings(albumIdArg)
    }

    fun onEvent(event: AlbumDetailsEvent){
        when(event){
            is AlbumDetailsEvent.SendReviewAlbum -> {
                viewModelScope.launch {
                    _loadingSendRating.value = true
                    val rating = Ratings(
                        rate = event.rate,
                        user = userRepository.getCurrentUser()!!,
                        album = albumDetails.value!!,
                        updatedAt = LocalDateTime.now(),
                        createdAt = LocalDateTime.now()
                    )
                    delay(1000)
                    ratingsRepository.addRating(rating)
                    loadRatings(_albumDetails.value!!.id)
                    _loadingSendRating.value=false
                }
            }
        }
    }

    private fun findAlbum(albumId:String?){
        if(albumId!=null){
            viewModelScope.launch {
                try {


                    _albumTest.value = musicBrainzApi.lookupAlbum(albumId)
                    _albumTest.value?.image = imagesService.getImages(albumId).images.first().image
                }catch (e: HttpException){
                    Log.e("Req error", e.response()?.raw()?.request?.headers.toString())
                    Log.e("Req error", e.response()?.raw()?.request?.url.toString())
                    Log.e("Req error", e.code().toString())
                    Log.e("Req error", e.response()?.body().toString())
                }

            //_albumDetails.value = albumRepository.getAlbumByID(albumId)
            }
        }
    }

    private fun loadRatings(albumId: String?){
        if (albumId!=null){
            viewModelScope.launch {
                _albumReviews.value = ratingsRepository.getRatingsByAlbum(albumId)
            }
        }
    }
}