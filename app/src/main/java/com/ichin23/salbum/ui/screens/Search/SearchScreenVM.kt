package com.ichin23.salbum.ui.screens.Search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ichin23.salbum.data.api.APIMusicBrainzService
import com.ichin23.salbum.data.api.APIReleasesImagesService
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.musicbrainz.releaseGroup.ReleaseGroup
import com.ichin23.salbum.data.api.dto.salbum.musicmetadata.MusicMetadataCacheDTO
import com.ichin23.salbum.domain.models.Album
import com.ichin23.salbum.domain.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class SearchScreenVM @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val salbumService: ApiSalbumService,
    private val musicBrainzService: APIMusicBrainzService,
    private val images: APIReleasesImagesService,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var _searchResult = MutableStateFlow<List<Album>>(arrayListOf())
    var searchResult = _searchResult.asStateFlow()

    var _searchTest = MutableStateFlow<List<MusicMetadataCacheDTO>>(arrayListOf())
    var searchTest = _searchTest.asStateFlow()

    var _loading = MutableStateFlow<Boolean>(false)
    var loading = _loading.asStateFlow()

    fun search(query:String){
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = salbumService.search(query)

//                response.releaseGroups.forEach {
//                    try{
//                        it.image=images.getImages(it.releases.first().id).images.first().image
//                    }catch (e: HttpException){
//                        it.image=null
//                    }
//                }

                _searchTest.value = response;
                Log.i("Response:", _searchTest.value.toString())
            }catch (e: HttpException){
                Log.e("Req error", e.response()?.raw()?.request?.headers.toString())
                Log.e("Req error", e.response()?.raw()?.request?.url.toString())
                Log.e("Req error", e.code().toString())
                Log.e("Req error", e.response()?.body().toString())
            }
//            delay(1000)
//            _searchResult.value = albumRepository.searchAlbum(query)
            _loading.value = false
        }
    }
}